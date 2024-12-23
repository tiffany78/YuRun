function setApprove(element) {
    resetButtons(element.parentNode);
    element.classList.add('approve');
    
    // Set hidden field status to "true"
    const statusField = element.closest('.detailText').querySelector('.statusField');
    statusField.value = 'true';
}

function setReject(element) {
    resetButtons(element.parentNode);
    element.classList.add('reject');
    
    // Set hidden field status to "false"
    const statusField = element.closest('.detailText').querySelector('.statusField');
    statusField.value = 'false';
}

function resetButtons(container) {
    const buttons = container.querySelectorAll('.button2, .approve, .reject');
    buttons.forEach(button => {
        button.classList.remove('approve', 'reject');
        button.classList.add('button2');
    });

    // Reset status field to "null"
    const statusField = container.closest('.detailText').querySelector('.statusField');
    statusField.value = 'null';
}