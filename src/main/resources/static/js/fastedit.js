document.addEventListener('DOMContentLoaded', () => {
    const titleInput = document.getElementById('title');
    const descriptionTextarea = document.getElementById('description');
    const ticketIdInput = document.getElementById('ticketId');
    const ticketId = parseInt(ticketIdInput.value, 10);

    if (isNaN(ticketId)) {
        console.error('Invalid ticket ID');
        return;
    }

    titleInput.addEventListener('blur', () => {
        updateField('title', titleInput.value, ticketId);
    });

    descriptionTextarea.addEventListener('blur', () => {
        updateField('description', descriptionTextarea.value, ticketId);
    });

    function updateField(fieldName, fieldValue, ticketId) {
        fetch(`/ticket/${ticketId}/fastedit?fieldName=${fieldName}&fieldValue=${encodeURIComponent(fieldValue)}`, {
            method: 'PATCH',
        })
            .then(response => {
                if (response.ok) {
                    if (fieldName === 'title') {
                        titleInput.classList.add('input-success');
                        titleInput.classList.remove('input-error');
                    } else {
                        descriptionTextarea.classList.add('input-success');
                        descriptionTextarea.classList.remove('input-error');
                    }
                } else {
                    if (fieldName === 'title') {
                        titleInput.classList.add('input-error');
                        titleInput.classList.remove('input-success');
                    } else {
                        descriptionTextarea.classList.add('input-error');
                        descriptionTextarea.classList.remove('input-success');
                    }
                }
            })
            .catch(error => {
                console.error('Error:', error);
                if (fieldName === 'title') {
                    titleInput.classList.add('input-error');
                    titleInput.classList.remove('input-success');
                } else {
                    descriptionTextarea.classList.add('input-error');
                    descriptionTextarea.classList.remove('input-success');
                }
            });
    }
});
