function register() {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const data = {
    username: username,
    password: password,
  };

  fetch("/api/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      if (response.status == 201) {
        return response.json();
      } else {
        throw new Error("Register failed with status code: " + response.status);
      }
    })
    .then((data) => {
      localStorage.setItem("token", data.token);
      window.location.href="/";
    })
    .catch((error) => {
      alert("Register failed");
    });
}
