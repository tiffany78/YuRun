document.addEventListener("DOMContentLoaded", () => {
    const elementsToAnimate = document.querySelectorAll(".left2, .right2");

    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                if (entry.target.classList.contains("left2")) {
                    entry.target.style.animation = "slideInFromLeft 1s ease-out forwards";
                } else if (entry.target.classList.contains("right2")) {
                    entry.target.style.animation = "slideInFromRight 1s ease-out forwards";
                }
            }
        });
    });

    elementsToAnimate.forEach((el) => observer.observe(el));
});