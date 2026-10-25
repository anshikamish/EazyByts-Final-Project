let previousArticleCount = 0;

document.addEventListener("DOMContentLoaded", () => {
  askNotificationPermission();
  fetchNewsFromAPI();
  fetchLatestNews();
  setInterval(fetchLatestNews, 120000); // Auto-refresh every 2 minutes
});

function askNotificationPermission() {
  if ("Notification" in window && Notification.permission !== "granted") {
    Notification.requestPermission();
  }
}

function showNewArticleNotification(title) {
  if ("Notification" in window && Notification.permission === "granted") {
    new Notification("📰 New Article Loaded", {
      body: title,
      icon: "https://cdn-icons-png.flaticon.com/512/21/21601.png"
    });
  }
}

// 🔄 ✅ Updated to fetch latest 10 articles only
function fetchLatestNews() {
  const selectedCountry = document.getElementById("country-select")?.value || "in"; // Default to India
  fetch(`/api/news/country?country=${selectedCountry}`)
    .then(response => response.json())
    .then(data => {
      console.log("✅ Latest Articles Loaded:", data);

      if (data.length > previousArticleCount) {
        const newArticles = data.length - previousArticleCount;
        previousArticleCount = data.length;
        if (newArticles > 0) {
          showNewArticleNotification(`${newArticles} new article(s) available`);
        }
      }

      renderNews(data);
    })
    .catch(err => {
      console.error("❌ Error fetching news:", err);
      document.getElementById("news-container").innerHTML =
        "<p style='color:red;'>❌ Error loading news. Please try again later.</p>";
    });
}

function fetchNewsByCountry() {
  const country = document.getElementById("country-select").value;
  if (!country) return;

  fetch(`/api/news/country?country=${country}`)
    .then(res => res.json())
    .then(data => {
      renderNews(data);
    })
    .catch(err => {
      console.error("❌ Error loading country news:", err);
      document.getElementById("news-container").innerHTML =
        "<p style='color:red;'>❌ Failed to load country news.</p>";
    });
}


function renderNews(data) {
  const container = document.getElementById("news-container");
  container.innerHTML = "";

  if (!data.length) {
    container.innerHTML = "<p>No news articles available.</p>";
    return;
  }

  data.forEach(article => {
    const card = document.createElement("div");
    card.className = "card";

    const safeContent = article.content || "📝 No content available.";
    const safeUrl = article.url ? `<a href="${article.url}" target="_blank">Read Full Article</a>` : "";
    const safeCategory = article.category || "Uncategorized";
    const shareText = encodeURIComponent(`Check out this article: ${article.title || "Untitled"}`);
    const shareLink = article.url || "https://yourdomain.com";

    const shareButtons = `
      <div style="margin-top: 10px;">
        <strong>Share:</strong>
        <a href="https://wa.me/?text=${shareText} ${shareLink}" target="_blank">WhatsApp</a> |
        <a href="https://twitter.com/intent/tweet?text=${shareText}&url=${shareLink}" target="_blank">Twitter</a> |
        <a href="https://www.linkedin.com/sharing/share-offsite/?url=${shareLink}" target="_blank">LinkedIn</a>
      </div>
    `;

    card.innerHTML = `
      <h3>${article.title || "Untitled"}</h3>
      <p><strong>Author:</strong> ${article.author || "Unknown"}</p>
      <p><strong>Source:</strong> ${article.source || "N/A"}</p>
      <p><strong>Category:</strong> ${safeCategory}</p>
      <p>${safeContent}</p>
      ${safeUrl}
      ${shareButtons}

      <div class="comment-box">
        <input type="text" id="comment-${article.id}" placeholder="Write a comment..." />
        <button onclick="submitComment(${article.id})">💬 Comment</button>
      </div>

      <div id="comments-${article.id}" class="comment-section">
        <p><em>Loading comments...</em></p>
      </div>
    `;

    container.appendChild(card);
    loadComments(article.id);
  });
}

// 🗨️ Comment Submission
function submitComment(articleId) {
  const input = document.getElementById(`comment-${articleId}`);
  if (!input) return;
  const content = input.value.trim();
  if (!content) return;

  fetch("/api/comments/save", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      commenterName: "Anonymous",
      text: content,
      article: { id: articleId }
    })
  })
    .then(res => {
      if (res.ok) {
        alert("✅ Comment added!");
        input.value = "";
        loadComments(articleId);
      } else {
        throw new Error("Comment failed");
      }
    })
    .catch(err => {
      console.error("❌ Error adding comment:", err);
      alert("❌ Failed to add comment.");
    });
}

// 🗨️ Load Comments
function loadComments(articleId) {
  fetch(`/api/comments/article/${articleId}`)
    .then(res => res.json())
    .then(data => {
      const section = document.getElementById(`comments-${articleId}`);
      section.innerHTML = "";
      if (!data.length) {
        section.innerHTML = "<p>No comments yet.</p>";
        return;
      }
      data.forEach(c => {
        const div = document.createElement("div");
        div.className = "comment";
        div.innerHTML = `
          <p><strong>${c.commenterName}</strong>:</p>
          <p>${c.text}</p><hr />
        `;
        section.appendChild(div);
      });
    })
    .catch(err => {
      console.error("❌ Error loading comments:", err);
      const section = document.getElementById(`comments-${articleId}`);
      if (section) {
        section.innerHTML = "<p style='color:red;'>❌ Failed to load comments.</p>";
      }
    });
}

// 🔍 Search
function searchNews() {
  const keyword = document.getElementById("search-input").value.trim();
  if (!keyword) return;
  fetch(`/api/news/search?keyword=${keyword}`)
    .then(res => res.json())
    .then(data => renderNews(data))
    .catch(err => {
      console.error("❌ Error searching news:", err);
      document.getElementById("news-container").innerHTML =
        "<p style='color:red;'>❌ Search failed. Try again.</p>";
    });
}

// 📂 Filter
function filterByCategory() {
  const category = document.getElementById("category-select").value;
  if (!category) return fetchLatestNews();
  fetch(`/api/news/category?category=${category}`)
    .then(res => res.json())
    .then(data => renderNews(data))
    .catch(err => {
      console.error("❌ Error filtering news:", err);
      document.getElementById("news-container").innerHTML =
        "<p style='color:red;'>❌ Unable to filter news. Try again.</p>";
    });
}

function fetchNewsFromAPI() {
  fetch("/api/newsapi/fetch", {
    method: "GET"
  })
    .then(res => res.text())
    .then(msg => {
      console.log("✅ News fetched from external API:", msg);
      fetchLatestNews(); // latest 10 firse reload karo
    })
    .catch(err => {
      console.error("❌ Failed to fetch from API:", err);
    });
}


// 🔒 Logout
function logoutUser() {
  localStorage.removeItem("userEmail");
  window.location.href = "login.html";
}
