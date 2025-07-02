document.addEventListener("DOMContentLoaded", () => {
  const email = localStorage.getItem("userEmail");

  // ⚠️ No email in localStorage → redirect to login
  if (!email) {
    window.location.href = "login.html";
    return;
  }

  // 🔐 Fetch user by email
  fetch(`http://localhost:8080/api/users/get?email=${encodeURIComponent(email)}`)
    .then(res => {
      if (!res.ok) {
        throw new Error("User not found");
      }
      return res.json();
    })
    .then(user => {
      // 🔍 Check admin role
      if (user.role === "ADMIN") {
        loadStats(); // ✅ Admin allowed → load dashboard stats
      } else {
        alert("⛔ Unauthorized access. Only admin can access dashboard.");
        window.location.href = "index.html"; // 🚫 Redirect non-admin users
      }
    })
    .catch(err => {
      console.error("❌ Error verifying user:", err);
      alert("⚠️ Session expired or invalid. Please login again.");
      window.location.href = "login.html";
    });
});

function loadStats() {
  // 🧑‍🤝‍🧑 Load users
  fetch("http://localhost:8080/api/users/all")
    .then(res => res.json())
    .then(data => {
      const usersOnly = data.filter(u => u.role === "USER");

      // Count
      document.getElementById("user-count").innerText = `Total Registered Users: ${usersOnly.length}`;

      // List
      const list = document.getElementById("user-list");
      list.innerHTML = "";
      usersOnly.forEach(user => {
        const li = document.createElement("li");
        li.innerText = `${user.name} (${user.email})`;
        list.appendChild(li);
      });
    })
    .catch(err => {
      console.error("❌ Error loading user stats:", err);
      document.getElementById("user-count").innerText = "❌ Failed to load user data.";
    });

  // 🗞️ Load news count + category stats
  fetch("http://localhost:8080/api/news/all")
    .then(res => res.json())
    .then(newsList => {
      // Total articles
      document.getElementById("news-count").innerText = newsList.length;

      // Count categories
      const categoryCount = {};
      newsList.forEach(news => {
        const category = news.category || "Uncategorized";
        categoryCount[category] = (categoryCount[category] || 0) + 1;
      });

      renderChart(categoryCount);
    })
    .catch(err => {
      console.error("❌ Error loading news:", err);
      document.getElementById("news-count").innerText = "❌ Failed to load news.";
    });
}

// 📊 Render category bar chart
function renderChart(categoryData) {
  const ctx = document.getElementById("categoryChart").getContext("2d");
  new Chart(ctx, {
    type: "bar",
    data: {
      labels: Object.keys(categoryData),
      datasets: [{
        label: "Top Categories",
        data: Object.values(categoryData),
        backgroundColor: "#007bff"
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: { display: false }
      }
    }
  });
}

// 🔒 Logout
function logoutUser() {
  localStorage.removeItem("userEmail");
  window.location.href = "login.html";
}
