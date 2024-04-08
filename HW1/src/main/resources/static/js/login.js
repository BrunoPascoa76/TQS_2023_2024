function login() {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const data = {
    username: username,
    password: password,
  };

  fetch("/api/login", {
    method: "POST",
    headers: {
      "Content-Type": 'application/json'
    },
    body: JSON.stringify(data),
  }).then((response) => {
    if (response.status == 20) {
      localStorage.setItem("token", response.json()["token"]);
      window.location.href = "/";
    } else {
      alert("login failed");
    }
  });
}
