
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

