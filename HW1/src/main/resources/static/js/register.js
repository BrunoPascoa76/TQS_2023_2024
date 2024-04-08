function register() {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const body = {
    username: username,
    password: password,
  };

  fetch("/api/register", {
    method: "POST",
    body: JSON.stringify(data),
  }).then((response) => {
    if (response.status == 201) {
      localStorage.setItem("token", response.json()["token"]);
      window.location.href = "/";
    } else {
      alert("login failed");
    }
  });
}
