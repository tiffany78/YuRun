document.addEventListener("DOMContentLoaded", () => {
    const elementsToAnimate = document.querySelectorAll(".left2, .right2");

    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                // Ketika elemen terlihat
                if (!entry.target.classList.contains("animated")) {
                    if (entry.target.classList.contains("left2")) {
                        entry.target.style.animation = "slideInFromLeft 1s ease-out forwards";
                    } else if (entry.target.classList.contains("right2")) {
                        entry.target.style.animation = "slideInFromRight 1s ease-out forwards";
                    }
                    entry.target.classList.add("animated");
                }
            } else {
                // Reset animasi saat elemen keluar dari viewport
                entry.target.style.animation = "none";
                entry.target.classList.remove("animated");
            }
        });
    }, { threshold: 0.1 }); // threshold rendah untuk menangkap perubahan masuk/keluar viewport

    elementsToAnimate.forEach((el) => observer.observe(el));
});