// src/main/resources/static/js/dashboard.js

function addVulnerability() {
    const vulnData = {
        cveId: document.getElementById('newCveId').value,
        title: document.getElementById('newTitle').value,
        severity: document.getElementById('newSeverity').value
    };

    if (!vulnData.cveId || !vulnData.title) {
        alert("Please fill in all fields.");
        return;
    }

    fetch('/api/vulnerabilities', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(vulnData),
        credentials: 'same-origin' 
    })
    .then(response => {
        if (response.status === 403) {
            alert("Access Denied: You must be logged in as an ADMIN to add vulnerabilities.");
            throw new Error("Forbidden");
        }
        if (response.status === 429) {
            alert("Rate limit exceeded! Please wait.");
            throw new Error("Rate Limited");
        }
        if (!response.ok) return response.text().then(text => { throw new Error(text) });
        return response.json();
    })
    .then(data => {
        location.reload(); 
    })
    .catch(error => console.error('Error:', error));
}

function searchBySeverity() {
    const severity = document.getElementById('severitySearch').value;
    if (!severity) return;

    fetch('/api/vulnerabilities/search?severity=' + severity)
        .then(response => response.json())
        .then(data => {
            updateTable(data);
        })
        .catch(error => console.error('Error searching:', error));
}

function updateTable(vulnerabilities) {
    const tbody = document.querySelector('tbody');
    tbody.innerHTML = ''; // Clear existing rows

    vulnerabilities.forEach(vuln => {
        const row = `
            <tr id="row-${vuln.id}">
                <td>${vuln.id}</td>
                <td>${vuln.cveId}</td>
                <td>${vuln.title}</td>
                <td class="${vuln.severity}">${vuln.severity}</td>
                <td>
                    <button class="delete-btn" onclick="deleteVuln(${vuln.id})">Delete</button>
                </td>
            </tr>`;
        tbody.innerHTML += row;
    });
}

function resetTable() {
    location.reload(); // Simplest way to get all data back

}function deleteVuln(id) {
    if (confirm("Are you sure you want to delete this vulnerability?")) {
        fetch('/api/vulnerabilities/' + id, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                // Remove the row from the table instantly without refreshing
                const row = document.getElementById('row-' + id);
                if (row) {
                    row.remove();
                    console.log(`Vulnerability ${id} deleted successfully.`);
                }
            } else {
                alert("Failed to delete the vulnerability. Please check your permissions.");
            }
        }).catch(error => {
            console.error('Error:', error);
            alert("An error occurred while trying to delete.");
        });
    }
}

function postNote() {
    const noteContent = document.getElementById('adminNote').value;
    if (!noteContent) return;

    fetch('/api/notes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ content: noteContent }),
        credentials: 'same-origin'
    })
    .then(response => {
        if (response.ok) location.reload();
        else alert("Unauthorized: Only Admins can post briefings.");
    });
}