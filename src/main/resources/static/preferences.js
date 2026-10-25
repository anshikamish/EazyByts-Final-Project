// ✅ Auto-fill email if saved in localStorage
document.addEventListener("DOMContentLoaded", () => {
  const savedEmail = localStorage.getItem("userEmail");
  if (savedEmail) {
    document.getElementById("email").value = savedEmail;
  }
});

// ✅ Save Preferences
function savePreferences(event) {
  event.preventDefault();

  const email = document.getElementById("email").value.trim();
  const keywords = document.getElementById("keywords").value.trim();
  const checkedBoxes = document.querySelectorAll("input[name='category']:checked");

  if (!email) {
    document.getElementById("response-message").innerText = "⚠️ Email is required.";
    return;
  }

  if (!keywords && checkedBoxes.length === 0) {
    document.getElementById("response-message").innerText = "⚠️ Please enter keywords or select categories.";
    return;
  }

  // Send one preference at a time (category + keyword + user)
  const user = { email: email };

  const savePromises = [];

  checkedBoxes.forEach(cb => {
    const preference = {
      keyword: keywords,
      category: cb.value,
      user: user
    };

    savePromises.push(
      fetch("/api/preferences/save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(preference)
      })
    );
  });

  Promise.all(savePromises)
    .then(responses => {
      if (responses.every(res => res.ok)) {
        document.getElementById("response-message").innerText = "✅ Preferences saved successfully!";
      } else {
        throw new Error("Some preferences failed.");
      }
    })
    .catch(error => {
      console.error("❌ Error saving preferences:", error);
      document.getElementById("response-message").innerText = "❌ Could not save preferences.";
    });
}
