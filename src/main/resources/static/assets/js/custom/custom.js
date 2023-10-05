
// clinic slider on home page (begin)
if ($('.owl-carousel.clinic-slider').length > 0) {
    $('.owl-carousel.clinic-slider').owlCarousel({
        loop: true,
        margin: 24,
        dots: false,
        nav: true,
        smartSpeed: 2000,
        navContainer: '.slide-nav-clinic',
        navText: ['<i class="fas fa-chevron-left"></i>', '<i class="fas fa-chevron-right"></i>'],
        responsive: {
            0: {
                items: 1
            },
            500: {
                items: 1
            },
            768: {
                items: 2
            },
            1000: {
                items: 3
            },
            1300: {
                items: 4
            }
        }
    })
}
// clinic slider on home page (end)


// filter search page
$('.search-filter .filter-details #apply-filter-btn').on('click', function () {
    searchFilter()
})

// add review
$('#review-doctor').on('submit', function (e) {
    e.preventDefault();

    reviewDoctor();
})
