const currentUserId = [[${currentUserId}]];

function updateRaceStatus(id_race, isJoining) {
    if (!isJoining && !confirm("Are you sure you want to exit the race?")) {
        return;
    }

    fetch(`/member/race/${isJoining ? "join" : "exit"}`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `id_race=${id_race}&id_user=${currentUserId}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(`Successfully ${isJoining ? "joined" : "exited"} the race.`);
            location.reload();
        } else {
            alert(`Failed to ${isJoining ? "join" : "exit"} the race.`);
        }
    })
    .catch(error => console.error("Error:", error));
}

function uploadRaceActivity(id_race) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/member/race/selectRace';
    
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'id_race';
    input.value = id_race;
    
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}