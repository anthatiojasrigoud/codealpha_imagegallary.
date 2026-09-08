/* =========================================
   CODEALPHA IMAGE GALLERY - PICNEST
========================================= */

/* =========================================
   SELECT ELEMENTS
========================================= */

const galleryGrid = document.getElementById("galleryGrid");
const cards = Array.from(
    document.querySelectorAll(".gallery-card")
);

const filterButtons = document.querySelectorAll(".filter-btn");
const searchInput = document.getElementById("searchInput");
const totalCount = document.getElementById("totalCount");
const noResults = document.getElementById("noResults");

/* Lightbox */
const lightbox = document.getElementById("lightbox");
const lightboxImage = document.getElementById("lightboxImage");
const lightboxTitle = document.getElementById("lightboxTitle");
const lightboxCategory = document.getElementById("lightboxCategory");
const lightboxDescription = document.getElementById(
    "lightboxDescription"
);

const currentNumber = document.getElementById("currentNumber");
const totalNumber = document.getElementById("totalNumber");

const closeBtn = document.getElementById("closeBtn");
const nextBtn = document.getElementById("nextBtn");
const prevBtn = document.getElementById("prevBtn");

/* Mobile Navigation */
const menuBtn = document.getElementById("menuBtn");
const navLinks = document.getElementById("navLinks");


/* =========================================
   VARIABLES
========================================= */

let activeCategory = "all";
let searchText = "";
let visibleCards = [];
let currentIndex = 0;


/* =========================================
   CATEGORY DESCRIPTIONS
========================================= */

const descriptions = {
    nature:
        "A beautiful moment captured from the natural world.",

    travel:
        "Discover beautiful places and unforgettable destinations.",

    animals:
        "A glimpse into the fascinating world of wildlife.",

    food:
        "Beautifully captured food and delicious moments."
};


/* =========================================
   FILTER + SEARCH GALLERY
========================================= */

function updateGallery() {

    visibleCards = [];

    cards.forEach(card => {

        const category =
            (card.dataset.category || "").toLowerCase();

        const title =
            (card.dataset.title || "").toLowerCase();

        const image =
            card.querySelector("img");

        const alt =
            (image?.alt || "").toLowerCase();

        /* Category matching */
        const matchesCategory =
            activeCategory === "all" ||
            category === activeCategory;

        /* Search matching */
        const matchesSearch =
            title.includes(searchText) ||
            category.includes(searchText) ||
            alt.includes(searchText);

        /* Show card */
        if (matchesCategory && matchesSearch) {

            card.style.display = "";

            visibleCards.push(card);

        } else {

            card.style.display = "none";
        }
    });


    /* =====================================
       UPDATE IMAGE COUNT
    ===================================== */

    totalCount.textContent =
        visibleCards.length;


    /* =====================================
       NO RESULTS MESSAGE
    ===================================== */

    if (visibleCards.length === 0) {

        noResults.classList.add("show");

    } else {

        noResults.classList.remove("show");
    }


    /* =====================================
       CLOSE LIGHTBOX IF FILTER CHANGES
    ===================================== */

    if (lightbox.classList.contains("active")) {

        closeLightbox();
    }
}


/* =========================================
   CATEGORY BUTTONS
========================================= */

filterButtons.forEach(button => {

    button.addEventListener("click", () => {

        /* Remove active class */
        filterButtons.forEach(btn => {
            btn.classList.remove("active");
        });

        /* Add active class */
        button.classList.add("active");

        /* Get selected category */
        activeCategory =
            button.dataset.filter.toLowerCase();

        /* Update gallery */
        updateGallery();
    });

});


/* =========================================
   SEARCH
========================================= */

if (searchInput) {

    searchInput.addEventListener("input", event => {

        searchText =
            event.target.value
                .toLowerCase()
                .trim();

        updateGallery();
    });

}


/* =========================================
   OPEN LIGHTBOX
========================================= */

cards.forEach(card => {

    card.addEventListener("click", () => {

        const index =
            visibleCards.indexOf(card);

        if (index !== -1) {

            currentIndex = index;

            openLightbox();
        }

    });

});


/* =========================================
   OPEN LIGHTBOX FUNCTION
========================================= */

function openLightbox() {

    if (visibleCards.length === 0) {
        return;
    }

    showImage(currentIndex);

    lightbox.classList.add("active");

    document.body.classList.add("lightbox-open");

    /* Fallback for CSS/body locking */
    document.body.style.overflow = "hidden";
}


/* =========================================
   SHOW IMAGE IN LIGHTBOX
========================================= */

function showImage(index) {

    if (visibleCards.length === 0) {
        return;
    }


    /* =====================================
       LOOP THROUGH IMAGES
    ===================================== */

    if (index < 0) {

        currentIndex =
            visibleCards.length - 1;

    } else if (index >= visibleCards.length) {

        currentIndex = 0;

    } else {

        currentIndex = index;
    }


    /* Get current card */
    const card =
        visibleCards[currentIndex];


    /* Get image */
    const image =
        card.querySelector("img");


    /* Get information */
    const title =
        card.dataset.title || "Beautiful Moment";

    const category =
        card.dataset.category || "Gallery";


    /* =====================================
       UPDATE LIGHTBOX IMAGE
    ===================================== */

    lightboxImage.src = image.src;

    lightboxImage.alt =
        image.alt || title;


    /* =====================================
       UPDATE TITLE
    ===================================== */

    lightboxTitle.textContent =
        title;


    /* =====================================
       UPDATE CATEGORY
    ===================================== */

    lightboxCategory.textContent =
        category;


    /* =====================================
       UPDATE DESCRIPTION
    ===================================== */

    lightboxDescription.textContent =
        descriptions[category.toLowerCase()] ||
        "A beautiful moment captured through photography.";


    /* =====================================
       UPDATE COUNTER
    ===================================== */

    currentNumber.textContent =
        currentIndex + 1;

    totalNumber.textContent =
        visibleCards.length;
}


/* =========================================
   NEXT IMAGE
========================================= */

if (nextBtn) {

    nextBtn.addEventListener("click", event => {

        event.stopPropagation();

        showImage(currentIndex + 1);

    });

}


/* =========================================
   PREVIOUS IMAGE
========================================= */

if (prevBtn) {

    prevBtn.addEventListener("click", event => {

        event.stopPropagation();

        showImage(currentIndex - 1);

    });

}


/* =========================================
   CLOSE LIGHTBOX
========================================= */

function closeLightbox() {

    lightbox.classList.remove("active");

    document.body.classList.remove("lightbox-open");

    document.body.style.overflow = "";

}


/* Close button */

if (closeBtn) {

    closeBtn.addEventListener(
        "click",
        closeLightbox
    );

}


/* =========================================
   CLOSE LIGHTBOX USING BACKDROP
========================================= */

lightbox.addEventListener("click", event => {

    if (
        event.target.classList.contains(
            "lightbox-backdrop"
        )
    ) {

        closeLightbox();
    }

});


/* =========================================
   KEYBOARD CONTROLS
========================================= */

document.addEventListener("keydown", event => {

    /* Only work when lightbox is open */

    if (
        !lightbox.classList.contains("active")
    ) {
        return;
    }


    switch (event.key) {

        case "ArrowRight":

            event.preventDefault();

            showImage(
                currentIndex + 1
            );

            break;


        case "ArrowLeft":

            event.preventDefault();

            showImage(
                currentIndex - 1
            );

            break;


        case "Escape":

            closeLightbox();

            break;
    }

});


/* =========================================
   TOUCH SWIPE
========================================= */

let touchStartX = 0;
let touchEndX = 0;


lightbox.addEventListener(
    "touchstart",
    event => {

        touchStartX =
            event.changedTouches[0].screenX;

    },
    { passive: true }
);


lightbox.addEventListener(
    "touchend",
    event => {

        touchEndX =
            event.changedTouches[0].screenX;

        handleSwipe();

    },
    { passive: true }
);


function handleSwipe() {

    const distance =
        touchEndX - touchStartX;


    /* Swipe left = next */

    if (distance < -50) {

        showImage(
            currentIndex + 1
        );

    }


    /* Swipe right = previous */

    else if (distance > 50) {

        showImage(
            currentIndex - 1
        );
    }

}


/* =========================================
   MOBILE NAVIGATION
========================================= */

if (menuBtn && navLinks) {

    menuBtn.addEventListener("click", () => {

        const isOpen =
            navLinks.classList.toggle("show");


        /* Change menu icon */

        const icon =
            menuBtn.querySelector("i");

        if (icon) {

            if (isOpen) {

                icon.classList.remove("fa-bars");

                icon.classList.add("fa-xmark");

            } else {

                icon.classList.remove("fa-xmark");

                icon.classList.add("fa-bars");
            }
        }


        /* Accessibility */

        menuBtn.setAttribute(
            "aria-expanded",
            isOpen
        );

    });


    /* Close mobile menu after clicking link */

    navLinks
        .querySelectorAll(".nav-link")
        .forEach(link => {

            link.addEventListener("click", () => {

                navLinks.classList.remove("show");

                const icon =
                    menuBtn.querySelector("i");

                if (icon) {

                    icon.classList.remove(
                        "fa-xmark"
                    );

                    icon.classList.add(
                        "fa-bars"
                    );
                }

                menuBtn.setAttribute(
                    "aria-expanded",
                    "false"
                );

            });

        });

}


/* =========================================
   ACTIVE NAVIGATION
========================================= */

const navItems =
    document.querySelectorAll(".nav-link");

const sections = [
    document.getElementById("home"),
    document.getElementById("gallery"),
    document.getElementById("about"),
    document.getElementById("contact")
];


function updateActiveNav() {

    const scrollPosition =
        window.scrollY + 180;


    let currentSection = "home";


    sections.forEach(section => {

        if (
            section &&
            scrollPosition >= section.offsetTop
        ) {

            currentSection =
                section.id;
        }

    });


    navItems.forEach(link => {

        const href =
            link.getAttribute("href");

        link.classList.toggle(
            "active",
            href === `#${currentSection}`
        );

    });

}


window.addEventListener(
    "scroll",
    updateActiveNav
);


/* =========================================
   NAVIGATION CLICK
========================================= */

navItems.forEach(link => {

    link.addEventListener("click", () => {

        navItems.forEach(item => {
            item.classList.remove("active");
        });

        link.classList.add("active");

    });

});


/* =========================================
   IMAGE ERROR HANDLING
========================================= */

document
    .querySelectorAll("img")
    .forEach(image => {

        image.addEventListener(
            "error",
            () => {

                image.style.display = "none";

                if (image.parentElement) {

                    image.parentElement.style.background =
                        "linear-gradient(135deg, #1e293b, #312e81)";
                }

            }
        );

    });


/* =========================================
   LIGHTBOX IMAGE ERROR
========================================= */

lightboxImage.addEventListener(
    "error",
    () => {

        lightboxImage.alt =
            "Image could not be loaded";

    }
);


/* =========================================
   PREVENT LIGHTBOX IMAGE DRAGGING
========================================= */

lightboxImage.addEventListener(
    "dragstart",
    event => {
        event.preventDefault();
    }
);


/* =========================================
   INITIALIZE GALLERY
========================================= */

updateGallery();

updateActiveNav();


/* =========================================
   SET MOBILE MENU ACCESSIBILITY
========================================= */

if (menuBtn) {

    menuBtn.setAttribute(
        "aria-expanded",
        "false"
    );

}