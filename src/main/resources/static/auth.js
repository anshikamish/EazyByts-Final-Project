// Register User
function registerUser(event) {
  event.preventDefault();

  const user = {
    name: document.getElementById("reg-name").value,
    email: document.getElementById("reg-email").value,
    password: document.getElementById("reg-password").value,
    role: "USER"
  };

  fetch("http://localhost:8080/api/users/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  })
    .then(res => res.json())
    .then(data => {
      document.getElementById("register-message").innerText = "✅ Registered successfully!";
      window.location.href = "login.html";
    })
    .catch(err => {
      document.getElementById("register-message").innerText = "❌ Error during registration.";
      console.error(err);
    });
}

// Login User
function loginUser(event) {
  event.preventDefault();

  const email = document.getElementById("login-email").value;
  const password = document.getElementById("login-password").value;

  fetch("http://localhost:8080/api/users/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, password })
  })
    .then(res => {
      if (!res.ok) {
        throw new Error("Login failed");
      }
      return res.json();
    })
    .then(data => {
      localStorage.setItem("userEmail", data.email); // ✅ Save email
      
      // ✅ Role check
      if (data.role === "ADMIN") {
        document.getElementById("login-message").innerText = "✅ Admin logged in!";
        window.location.href = "dashboard.html";
      } else {
        document.getElementById("login-message").innerText = "✅ User logged in!";
        window.location.href = "index.html";
      }
    })
    .catch(err => {
      document.getElementById("login-message").innerText = "❌ Invalid credentials.";
      console.error(err);
    });
}

