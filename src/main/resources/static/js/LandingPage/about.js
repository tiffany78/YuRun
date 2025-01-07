const teamMembers = [
    {
        name: "John Doe",
        title: "This app transformed my running experience. I can now track my progress and set new goals easily.",
        color: "#ff4500",
        image: "/images/lebron.jpg" // Menambahkan URL gambar untuk John
    },
    {
        name: "Jane Smith",
        title: "The personalized training plans helped me prepare for my first marathon. Highly recommend it!",
        color: "#2563eb",
        image: "/images/jane.jpg" // Menambahkan URL gambar untuk Jane
    },
    {
        name: "Alice Johnson",
        title: "The personalized plans and motivating features have made my fitness journey enjoyable and rewarding!",
        color: "#059669",
        image: "/images/alice.jpg" // Menambahkan URL gambar untuk Alice
    },
    {
        name: "Michael Brown",
        title: "I enjoy tracking my daily runs and seeing my progress over time. A great app for runners!",
        color: "#ff4500",
        image: "/images/kobe.jpg" // Menambahkan URL gambar untuk Michael
    }
];

let currentIndex = 0;
let isAnimating = false;
let autoplayInterval;
let isAutoplayEnabled = true;
const slideInterval = 5000; // 5 seconds per slide

function updateProgressBar() {
    const progressBar = document.querySelector('.progress-bar');
    progressBar.style.width = '0%';
    if (isAutoplayEnabled) {
        progressBar.style.transition = `width ${slideInterval}ms linear`;
        setTimeout(() => {
            progressBar.style.width = '100%';
        }, 50);
    }
}

function updateMemberCard(direction) {
    const memberCard = document.querySelector('.member-card');
    const nameElement = document.querySelector('.member-name');
    const titleElement = document.querySelector('.member-title');
    const accentBlock = document.querySelector('.accent-block');
    const dots = document.querySelectorAll('.dot');
    const progressBar = document.querySelector('.progress-bar');
    const memberImage = document.querySelector('.member-image'); // Menambahkan selector untuk gambar

    memberCard.classList.add(`slide-out-${direction}`);
    progressBar.style.transition = 'none';
    progressBar.style.width = '0%';

    setTimeout(() => {
        // Memperbarui informasi anggota
        nameElement.textContent = teamMembers[currentIndex].name;
        titleElement.textContent = teamMembers[currentIndex].title;
        accentBlock.style.backgroundColor = teamMembers[currentIndex].color;

        // Memperbarui gambar anggota
        memberImage.src = teamMembers[currentIndex].image; // Mengubah gambar anggota

        dots.forEach((dot, index) => {
            dot.classList.toggle('active', index === currentIndex);
        });

        memberCard.classList.remove(`slide-out-${direction}`);
        isAnimating = false;
        updateProgressBar();
    }, 500);
}


function nextSlide() {
    if (isAnimating) return;
    isAnimating = true;
    currentIndex = (currentIndex + 1) % teamMembers.length;
    updateMemberCard('left');
}

function prevSlide() {
    if (isAnimating) return;
    isAnimating = true;
    currentIndex = (currentIndex - 1 + teamMembers.length) % teamMembers.length;
    updateMemberCard('right');
}

function startAutoplay() {
    if (!autoplayInterval) {
        autoplayInterval = setInterval(nextSlide, slideInterval);
        updateProgressBar();
    }
}

function stopAutoplay() {
    if (autoplayInterval) {
        clearInterval(autoplayInterval);
        autoplayInterval = null;
        const progressBar = document.querySelector('.progress-bar');
        progressBar.style.transition = 'none';
        progressBar.style.width = '0%';
    }
}

function toggleAutoplay() {
    const playPauseButton = document.querySelector('.play-pause-button');
    isAutoplayEnabled = !isAutoplayEnabled;

    if (isAutoplayEnabled) {
        playPauseButton.innerHTML = `
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 9v6m4-6v6m7-3a9 9 0 11-18 0 9 9 0 0118 0z" />
  </svg>
`;
        startAutoplay();
    } else {
        playPauseButton.innerHTML = `
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z" />
    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
  </svg>
`;
        stopAutoplay();
    }
}

// Start autoplay when the page loads
startAutoplay();

// Pause autoplay when the user hovers over the slider
document.querySelector('.slider-container').addEventListener('mouseenter', stopAutoplay);
document.querySelector('.slider-container').addEventListener('mouseleave', () => {
    if (isAutoplayEnabled) {
        startAutoplay();
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const image = document.querySelector('.picture'); 
    const images = ['/images/run1.png', '/images/run2.png', '/images/run3.png']; // Daftar gambar
    let currentIndex = 0;

    setInterval(() => {
        currentIndex = (currentIndex + 1) % images.length; // Update index gambar
        image.src = images[currentIndex]; // Ganti src gambar
    }, 2000); // Ganti gambar setiap 2 detik
});
