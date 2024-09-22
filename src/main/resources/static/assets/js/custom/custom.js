
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
// reset filter page
$('.search-filter .filter-details #reset-filter-btn').on('click', function () {
    window.location.replace('/search');
})

// add review
$('#review-doctor').on('submit', function (e) {
    e.preventDefault();

    reviewDoctor();
})

// book appointment
$('#book-appointment #appointment-communicationId select.form-select').on('change', function (e) {
    addCommunicationInfo($(this).val());
})
$('#book-appointment').on('submit', function (e) {
    e.preventDefault();

    bookAppointment();
})

// book appointment on doctor search
$('.doctor-content #book-appointment-button').on('click', function () {
    let doctorName = $(this).parent().parent().parent().children('.doc-info-left').children('.doc-info-cont').children('.doc-name').attr('data')
    let doctorId = $(this).attr('data')

    $('#book-appointment #appointment-doctorName input[type="text"]').val(doctorName)
    $('#book-appointment #appointment-doctorId').val(doctorId)
})

// reset password
$('#reset-password').on('submit', function (e) {
    e.preventDefault();

    resetPassword()
})

// select role
$('.login-content-info .account-content .signup-option-btns .signup-btn-info').on('click', function () {
    $('.login-content-info .account-content .signup-option-btns .signup-btn-info').removeClass('active')
    $(this).addClass('active')
    $('.login-content-info .account-content #first .widget-btn >a').removeClass('disabled')
})

// next button on first step
$('.login-content-info .account-content .widget-set .widget-content #first .widget-btn >a').on('click', function () {
    let role = $('.login-content-info .account-content .signup-option-btns input[type="radio"]:checked').val();
    addUserAccountRole(role)
})

// second step
$('#create-account').on('submit', function (e) {
    e.preventDefault();

    checkUserAccountInfo()
})

// get verification code on third step
$('fieldset#third #verify-account .forgot-link').on('click', function (e) {
    getEmailVerificationCode()
})

// verify account on third step
$('fieldset#third #verify-account').on('submit', function (e) {
    e.preventDefault();

    verifyAccount()
})

// rating star
$('#review-doctor #review-rating .star-rating >label').mouseenter( function () {
    let current = parseInt($(this).attr('data-id'))

    for (let i = 1; i <= current; i ++)
        $(`#review-doctor #review-rating .star-rating > label[data-id="${i}"]`).addClass('fill')

    for (let j = current + 1; j <= 5; j ++)
        $(`#review-doctor #review-rating .star-rating > label[data-id="${j}"]`).addClass('empty')
})
$('#review-doctor #review-rating .star-rating >label').mouseleave( function () {
    for (let j = 1; j <= 5; j ++) {
        $(`#review-doctor #review-rating .star-rating > label[data-id="${j}"]`).removeClass('fill')
        $(`#review-doctor #review-rating .star-rating > label[data-id="${j}"]`).removeClass('empty')
    }
})

// search in home page
$('#search-doctor').on("submit", function (e) {
    e.preventDefault();

    let search = $(this).find('#search').val()
    let location = $(this).find('#location').val()

    let pathVariable = '';

    if (search != null && search !== '') {
        if (pathVariable === '')
            pathVariable += 'q=' + search;
        else
            pathVariable += '&q=' + search;
    }
    if (location != null && location !== '') {
        if (pathVariable === '')
            pathVariable += 'loc=' + location;
        else
            pathVariable += '&loc=' + location;
    }

    let url = '/search?' + pathVariable;

    window.location.replace(url);
})

// remove filter item in search page
$('#doctors-found-area .filterItem .removeFilter').on('click', function () {
    let query = window.location.search;
    const params = new URLSearchParams(query)
    params.delete($(this).attr('data-value'))
    query = params.toString()

    window.location.replace('/search?' + query);
})

// sort by in search page
$('.doctor-content .search-body .doctor-filter-info .doctor-filter-inner .doctor-filter-option .doctor-filter-select #sort-by').on('change', function () {
    let filterValue = $(this).val()
    let filterName = $(this).attr('data-value')

    let query = window.location.search;
    const params = new URLSearchParams(query)
    params.delete(filterName)
    params.append(filterName, filterValue)
    query = params.toString()

    window.location.replace('/search?' + query);
})
