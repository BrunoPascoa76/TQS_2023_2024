function reserve(btn) {
  const btnId = btn.id;
  const tripId = btnId.slice(3);
  const seat = document.getElementById("seat" + tripId);
  const seatNum = parseInt(seat.value);
  const token = localStorage.getItem("token");
  const data = {
    "trip":{
      "id":tripId
    },
    "seat": seatNum,
  };
  if (token) {
    fetch("/api/trips/schedule", {
      method: "POST",
      headers: {
        "Token": `${token}`,
        "Content-Type": 'application/json'
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
