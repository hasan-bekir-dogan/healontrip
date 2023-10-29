
function searchFilter() {
    let gender, specialist, experience, rating;

    $('.search-filter .filter-details #gender input:checked').each(function () {
        if (gender == null)
            gender = $(this).val()
        else
            gender += ',' + $(this).val()
    })
    $('.search-filter .filter-details #speciality input:checked').each(function () {
        if (specialist == null)
            specialist = $(this).val()
        else
            specialist += ',' + $(this).val()
    })
    $('.search-filter .filter-details #experience input:checked').each(function () {
        if (experience == null)
            experience = $(this).val()
        else
            experience += ',' + $(this).val()
    })
    $('.search-filter .filter-details #rating input:checked').each(function () {
        if (rating == null)
            rating = $(this).val()
        else
            rating += ',' + $(this).val()
    })

    let pathVariable = '';

    if (gender != null) {
        if (pathVariable === '')
            pathVariable += 'gen=' + gender;
        else
            pathVariable += '&gen=' + gender;
    }
    if (specialist != null) {
        if (pathVariable === '')
            pathVariable += 'spe=' + specialist;
        else
            pathVariable += '&spe=' + specialist;
    }
    if (experience != null) {
        if (pathVariable === '')
            pathVariable += 'exp=' + experience;
        else
            pathVariable += '&exp=' + experience;
    }
    if (rating != null) {
        if (pathVariable === '')
            pathVariable += 'rat=' + rating;
        else
            pathVariable += '&rat=' + rating;
    }

    let url = '?' + pathVariable;

    window.location.replace(url);
}

function disableItems(list) {
    for (let i = 0; i < list.length; i++) {
        $(list[i]).prop('disabled', true)
    }
}

function enableItems(list) {
    for (let i = 0; i < list.length; i++) {
        $(list[i]).prop('disabled', false)
    }
}

function clearItems() {
    // define query selector
    let ratingStarSelector = '#review-doctor #review-rating input[type="radio"]:checked'
    let detailSelector = '#review-doctor #review-detail textarea'
    let termsAcceptSelector = '#review-doctor #review-terms-accept input[type="checkbox"]'

    // clear items
    $(ratingStarSelector).prop('checked', false)
    $(detailSelector).val('')
    $(termsAcceptSelector).prop('checked', false)
}

function hideErrors() {
    hideSuccessMessage()

    let jQuerySelector = `#review-errors`

    // remove error
    $(jQuerySelector).remove()
}

function showErrors(errorText) {
    let message = `<div id="review-errors" class="alert alert-danger alert-dismissible fade show" role="alert">
                           <ul>
                                ${errorText}
                           </ul>
                           <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                       </div>`;

    $(message).insertBefore($('#review-doctor'));

    let formErrorArea = `#review-errors`;
    let headerHeight = $('.header').height()
    let position = $(formErrorArea).offset().top - headerHeight;
    $("html, body").animate({ scrollTop: position }, "slow");
}

function hideSuccessMessage() {
    $('#review-success').remove()
}

function showSuccessMessage() {
    hideSuccessMessage()

    let message = ` <div id="review-success" class="alert alert-success alert-dismissible fade show" role="alert">
                        Review has been added successfully.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>`;

    $(message).insertBefore($('#review-doctor'));

    let formErrorArea = `#review-success`;
    let headerHeight = $('.header').height()
    let position = $(formErrorArea).offset().top - headerHeight;
    $("html, body").animate({ scrollTop: position }, "slow");
}

function addReviewHtml(reviewList, reviewInfo) {
    let reviews = '';

    for (let i = 0; i < reviewList.length; i++) {

        reviews += `  <li>
                            <div class="comment">
                                <img src="/${reviewList[i].patientProfileImgSrc}" class="avatar avatar-sm rounded-circle" alt="${reviewList[i].patientProfileImgAlt}">
                                <div class="comment-body">
                                    <div class="meta-data">
                                        <span class="comment-author">${reviewList[i].patientUserName}</span>
                                        <span class="comment-date">${reviewList[i].createdDate}</span>
                                        <div class="review-count rating">
                                            <i class="fas fa-star ${(reviewList[i].rating >= 1) ? 'filled' : ''}"></i>
                                            <i class="fas fa-star ${(reviewList[i].rating >= 2) ? 'filled' : ''}"></i>
                                            <i class="fas fa-star ${(reviewList[i].rating >= 3) ? 'filled' : ''}"></i>
                                            <i class="fas fa-star ${(reviewList[i].rating >= 4) ? 'filled' : ''}"></i>
                                            <i class="fas fa-star ${(reviewList[i].rating === 5) ? 'filled' : ''}"></i>
                                        </div>
                                    </div>
                                    <p class="comment-content">${reviewList[i].detail}</p>
                                </div>
                            </div>
                        </li>`;
    }

    // add review html
    $('#doc_reviews .review-listing .comments-list').html(reviews)

    // change review rating average and count
    $('.doctor-widget .doc-info-left .doc-info-cont .rating .rating-avg').html(reviewInfo.ratingAvg)
    $('.doctor-widget .doc-info-left .doc-info-cont .rating .rating-count').html(reviewInfo.ratingCount + ' Reviews')
    $('#show-all-reviews-btn >strong').html(reviewInfo.ratingCount)
}

function getReviewData() {
    // define query selector
    let ratingStarSelector = '#review-doctor #review-rating input[type="radio"]:checked'
    let detailSelector = '#review-doctor #review-detail textarea'
    let termsAcceptSelector = '#review-doctor #review-terms-accept input[type="checkbox"]'
    let doctorIdSelector = '#doctor-id'

    // disable input and button
    let affectedItemList = []

    affectedItemList.push(ratingStarSelector)
    affectedItemList.push(detailSelector)
    affectedItemList.push(termsAcceptSelector)
    affectedItemList.push('#review-doctor #add-review-btn button[type="submit"]')

    disableItems(affectedItemList)


    // get data
    let doctorId = $(doctorIdSelector).val()
    let ratingStar;

    switch ($(ratingStarSelector).val()) {
        case 'star-1':
            ratingStar = 1;
            break;
        case 'star-2':
            ratingStar = 2;
            break;
        case 'star-3':
            ratingStar = 3;
            break;
        case 'star-4':
            ratingStar = 4;
            break;
        case 'star-5':
            ratingStar = 5;
            break;
        default:
            ratingStar = 0;
    }

    let detail = $(detailSelector).val()

    let termsAccept = $(termsAcceptSelector).prop('checked')

    if(termsAccept === undefined)
        termsAccept = false;


    // ********** form data (begin) ********** //
    let formData = new FormData();

    // profile photo
    formData.append("doctorId", doctorId);
    formData.append("rating", ratingStar);
    formData.append("detail", detail);
    formData.append("termsAccept", termsAccept);
    // ********** form data (end) ********** //


    return {
        affectedItemList,
        formData
    };
}

async function reviewDoctor() {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let resultObject = getReviewData();
    let formData = resultObject.formData;
    let affectedItemList = resultObject.affectedItemList;

    let response = await fetch($('#review-doctor').attr('action'), {
        headers: {
            'X-CSRF-Token': token,
            'X-CSRF-HEADER': header
        },
        method: $('#review-doctor').attr('method'),
        enctype: 'multipart/form-data',
        body: formData
    });

    let res = await response.json()

    if (res.status === 'success') { // success
        // enable items
        enableItems(affectedItemList)

        // hide errors
        hideErrors()

        // show success message
        showSuccessMessage()

        // add to review html
        addReviewHtml(res.reviewList, res.reviewInfo)

        // clear items
        clearItems()
    }
    else { // error

        // hide errors
        hideErrors()

        let errorText = '';

        for (let j = 0; j < res.length; j++) {
            errorText += `<li>${res[j].defaultMessage}</li>`;
        }

        // show errors
        if (res.errors != null) {
            for (let j = 0; j < res.errors.length; j++)
                errorText += `<li>${res.errors[j].defaultMessage}</li>`;

            // show errors
            showErrors(errorText)
        }

        // enable items
        enableItems(affectedItemList)
    }
}


function addCommunicationInfoHTML(data) {
    let dataHtml = '';

    if(data.name == 'E-MAIL' || data.name == 'ALL') {
        dataHtml += `   <div class="col-md-6 temp-communication-items">
                            <div id="patient-email" class="form-group">
                                <label>Your Email <span class="text-danger">*</span></label>
                                <input type="email" class="form-control" value="${data.email}" readonly>
                            </div>
                        </div>`;
    }

    if(data.name == 'PHONE' || data.name == 'WHATSAPP' || data.name == 'ALL') {
        dataHtml += `   <div class="col-md-6 temp-communication-items">
                            <div id="appointment-patientPhoneNumber" class="form-group">
                                <label>Your Phone Number <span class="text-danger">*</span></label>
                                <input type="number" class="form-control" value="${data.phone}">
                            </div>
                        </div>`;
    }


    // add review html
    $('#book-appointment .temp-communication-items').remove()
    $(dataHtml).insertAfter($('#book-appointment #preferred-communication-method'))
}

async function addCommunicationInfo(communicationId) {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let response = await fetch('/doctors/communication-info/' + communicationId, {
        headers: {
            'X-CSRF-Token': token,
            'X-CSRF-HEADER': header
        },
        method: 'GET',
        enctype: 'multipart/form-data'
    });

    let res = await response.json()

    if (res.status === 'success') { // success
        // add to communication html
        addCommunicationInfoHTML(res.data)
    }
    else { // error
        toastr.error('Something went wrong.');
    }
}


function clearAppointmentItems() {
    // define query selector
    let communicationSelector = '#book-appointment #appointment-communicationId select.form-select'
    let shortExplanationSelector = '#book-appointment #appointment-shortExplanation textarea'
    let termsAcceptSelector = '#book-appointment #appointment-termsAccept input[type="checkbox"]'

    // clear items
    $(communicationSelector).val('')
    $(shortExplanationSelector).val('')
    $(termsAcceptSelector).prop('checked', false)
    $('#book-appointment .temp-communication-items').remove()
}

function hideAppointmentErrors() {
    let jQuerySelector = `#book-appointment .form-error`

    // remove error
    $(jQuerySelector).removeClass('form-error')
    $(`#book-appointment .appointment-text-danger`).remove()
}

function showAppointmentErrors(field, message) {
    let jQuerySelector = `#book-appointment #appointment-${field}`

    // show error
    $(jQuerySelector).addClass('form-error')
    $(jQuerySelector).append(`<p class="appointment-text-danger text-danger error-appointment-${field}">${message}</p>`)
}

function hideAppointmentSuccessMessage() {
    $('#appointment-success').remove()
}

function showAppointmentSuccessMessage() {
    hideAppointmentSuccessMessage()

    let message = ` <div id="appointment-success" class="alert alert-success alert-dismissible fade show" role="alert">
                        Appointment has been booked successfully.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>`;

    $(message).insertBefore($('#book-appointment'));

    let formErrorArea = `#appointment-success`;
    let headerHeight = $('.header').height()
    let position = $(formErrorArea).offset().top - headerHeight;
    $("html, body").animate({ scrollTop: position }, "slow");
}

function getAppointmentData() {
    // define query selector
    let communicationSelector = '#book-appointment #appointment-communicationId select.form-select'
    let patientPhoneSelector = '#book-appointment #appointment-patientPhoneNumber input[type="number"]'
    let shortExplanationSelector = '#book-appointment #appointment-shortExplanation textarea'
    let termsAcceptSelector = '#book-appointment #appointment-termsAccept input[type="checkbox"]'
    let doctorIdSelector = '#doctor-id'

    // disable input and button
    let affectedItemList = []

    affectedItemList.push(communicationSelector)
    affectedItemList.push(patientPhoneSelector)
    affectedItemList.push(termsAcceptSelector)
    affectedItemList.push('#book-appointment #book-appointment-btn button[type="submit"]')

    disableItems(affectedItemList)


    // get data
    let doctorId = $(doctorIdSelector).val()
    let communicationId = $(communicationSelector).val()
    let patientPhoneNumber = $(patientPhoneSelector).val();
    let shortExplanation = $(shortExplanationSelector).val();

    let termsAccept = $(termsAcceptSelector).prop('checked')

    if(termsAccept === undefined)
        termsAccept = false;


    // ********** form data (begin) ********** //
    let formData = new FormData();

    // profile photo
    formData.append("doctorId", doctorId);
    formData.append("communicationId", communicationId);
    formData.append("patientPhoneNumber", patientPhoneNumber);
    formData.append("shortExplanation", shortExplanation);
    formData.append("termsAccept", termsAccept);
    // ********** form data (end) ********** //


    return {
        affectedItemList,
        formData
    };
}

async function bookAppointment() {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let resultObject = getAppointmentData();
    let formData = resultObject.formData;
    let affectedItemList = resultObject.affectedItemList;

    let response = await fetch($('#book-appointment').attr('action'), {
        headers: {
            'X-CSRF-Token': token,
            'X-CSRF-HEADER': header
        },
        method: $('#book-appointment').attr('method'),
        enctype: 'multipart/form-data',
        body: formData
    });

    let res = await response.json()

    if (res.status === 'login') { // not auth or not patient
        location.replace('/login')
    }
    else if (res.status === 'success') { // success
        // enable items
        enableItems(affectedItemList)

        // hide errors
        hideAppointmentErrors()

        // show success message
        showAppointmentSuccessMessage()

        // clear items
        clearAppointmentItems()
    }
    else { // error
        // hide success message
        hideAppointmentSuccessMessage()

        // hide errors
        hideAppointmentErrors()

        // show errors
        if (res.errors != null) {
            for (let j = 0; j < res.errors.length; j++) {

                showAppointmentErrors(
                    res.errors[j].field,
                    res.errors[j].defaultMessage
                )
            }

        }

        let formErrorArea = `#book-appointment .form-error`;
        let headerHeight = $('.header').height()
        let position = $(formErrorArea).offset().top - headerHeight;
        $("html, body").animate({ scrollTop: position }, "slow");

        // enable items
        enableItems(affectedItemList)
    }
}


function clearResetPasswordItems() {
    // define query selector
    let passwordSelector = '#reset-password #password input'
    let passwordRepeatSelector = '#reset-password #passwordRepeat input'

    // clear items
    $(passwordSelector).val('')
    $(passwordRepeatSelector).val('')
}

function hideResetPasswordErrors() {
    let jQuerySelector = `#reset-password .form-error`

    // remove error alert message
    $('#reset-password-fail').remove()

    // remove error inline
    $(jQuerySelector).removeClass('form-error')
    $(`#reset-password .reset-password-text-danger`).remove()
}

function showResetPasswordErrors(field, message) {
    let jQuerySelector = `#reset-password #${field}`

    // show error alert message
    if ($(jQuerySelector).html() == '' || $(jQuerySelector).html() == undefined) {

        if ($('#reset-password-fail').html() == '' || $('#reset-password-fail').html() == undefined) {
            let messagefail = ` <div id="reset-password-fail" class="alert alert-danger" role="alert">
                                    <ul>
                                        <li>${message}</li>
                                    </ul>
                                </div>`;

            $(messagefail).insertBefore($('#reset-password'));
        } else {
            $('#reset-password-fail >ul').append(`<li>${message}</li>`)
        }
    }

    // show error inline
    $(jQuerySelector).addClass('form-error')
    $(jQuerySelector).append(`<p class="reset-password-text-danger text-danger error-reset-password-${field}">${message}</p>`)
}

function hideResetPasswordSuccessMessage() {
    $('#reset-password-success').remove()
}

function showResetPasswordSuccessMessage() {
    hideResetPasswordSuccessMessage()

    let message = ` <div id="reset-password-success" class="alert alert-success alert-dismissible fade show" role="alert">
                        Your password has been changed successfully.
                        <br>
                        You will be redirected to login page in <span id="count-down">3</span> second.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>`;

    $(message).insertBefore($('#reset-password'));

    let formErrorArea = `#reset-password-success`;
    let headerHeight = $('.header').height()
    let position = $(formErrorArea).offset().top - headerHeight;
    $("html, body").animate({ scrollTop: position }, "slow");

    // count down and redirect to login page
    let count = 2;
    let countdown = setInterval(function () {
        $("#reset-password-success #count-down").html(count);

        if (count == 1) {
            setTimeout(function () {
                window.location.replace("/login");
            },1000);

            clearInterval(countdown)
        }

        count --
    }, 1000)
}

function getResetPasswordData() {
    // define query selector
    let userIdSelector = '#reset-password #userId'
    let resetTokenSelector = '#reset-password #resetToken'
    let passwordSelector = '#reset-password #password input'
    let passwordRepeatSelector = '#reset-password #passwordRepeat input'

    // disable input and button
    let affectedItemList = []

    affectedItemList.push(userIdSelector)
    affectedItemList.push(resetTokenSelector)
    affectedItemList.push(passwordSelector)
    affectedItemList.push(passwordRepeatSelector)
    affectedItemList.push('#reset-password button[type="submit"]')

    disableItems(affectedItemList)


    // get data
    let userId = $(userIdSelector).val()
    let resetToken = $(resetTokenSelector).val()
    let password = $(passwordSelector).val();
    let passwordRepeat = $(passwordRepeatSelector).val();


    // ********** form data (begin) ********** //
    let formData = new FormData();

    // profile photo
    formData.append("userId", userId);
    formData.append("resetToken", resetToken);
    formData.append("password", password);
    formData.append("passwordRepeat", passwordRepeat);
    // ********** form data (end) ********** //


    return {
        affectedItemList,
        formData
    };
}

async function resetPassword() {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let resultObject = getResetPasswordData();
    let formData = resultObject.formData;
    let affectedItemList = resultObject.affectedItemList;

    let response = await fetch($('#reset-password').attr('action'), {
        headers: {
            'X-CSRF-Token': token,
            'X-CSRF-HEADER': header
        },
        method: $('#reset-password').attr('method'),
        enctype: 'multipart/form-data',
        body: formData
    });

    let res = await response.json()

    if (res.status === 'success') { // success
        // enable items
        enableItems(affectedItemList)

        // hide errors
        hideResetPasswordErrors()

        // show success message
        showResetPasswordSuccessMessage()

        // clear items
        clearResetPasswordItems()
    }
    else { // error
        // hide success message
        hideResetPasswordSuccessMessage()

        // hide errors
        hideResetPasswordErrors()

        // show errors
        if (res.errors != null) {
            for (let j = 0; j < res.errors.length; j++) {
                showResetPasswordErrors(
                    res.errors[j].field,
                    res.errors[j].defaultMessage
                )
            }
        }

        let formErrorArea = `#reset-password .form-error`;
        let headerHeight = $('.header').height()
        let position = $(formErrorArea).offset().top - headerHeight;
        $("html, body").animate({ scrollTop: position }, "slow");

        // enable items
        enableItems(affectedItemList)
    }
}
