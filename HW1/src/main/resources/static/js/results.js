function updateSeatAvailability(btn) {
  const btnId = element.Id;
  const tripId = btnId.slice(3);
  const seat = document.getElementById("seat" + tripId);
  const seatNum = parseInt(seat.value);
  const token = localStorage.get("token");
  const data = {
    tripId: tripId,
    value: seatNum,
  };
  if (token) {
    fetch("/api/trips/schedule", {
      method: "POST",
      headers: {
        token: `${token}`, // Assuming token is stored with prefix "Bearer "
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if(response.status==201){
            alert("scheduling successful");
        }else{
            alert("scheduling failed");
        }
      });
  } else {
    alert("not logged in!");
    window.location.href = "/login";
  }
}
