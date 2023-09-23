
let addedClinicImages = [];
let deletedClinicImageList = [];
let deletedEducationList = [];
let deletedExperienceList = [];
let deletedAwardList = [];
let deletedMembershipList = [];
let edu_t_index = 0;
let exp_t_index = 0;
let award_t_index = 0;
let mem_t_index = 0;

function previewBlogImage() {
    const [file] = $('.doctor-add-blog .service-fields .service-upload #image')[0].files
    let imageHtml = "";

    if (file) {
        let imageExistyn = $('.doctor-add-blog .service-fields #uploadPreview .upload-wrap .upload-images').html();

        if (imageExistyn !== '' && imageExistyn !== undefined) {
            $('.doctor-add-blog .service-fields #uploadPreview .upload-wrap .upload-images >img').attr('src', URL.createObjectURL(file));
        }
        else {
            imageHtml = `<ul class="upload-wrap">
                            <li>
                                <div class="upload-images">
                                    <img alt="Blog Image" src="` + URL.createObjectURL(file) + `">
                                </div>
                            </li>
                        </ul>`;

            $('.doctor-add-blog .service-fields #uploadPreview').html(imageHtml);
        }
    }
}

function previewProfileImage() {
    const [file] = $('#profile-settings #user-profile-photo input[type="file"]')[0].files

    if (file) {
        $('#profile-settings #user-profile-photo .profile-img >img').attr('src', URL.createObjectURL(file));
    }
}

function changeBlogStatus(id, status) {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    let data = {
        "id": id,
        "status": status
    }

    $.ajax({
        url: "/blogs-crm/change-blog-status",
        method: "POST",
        dataType: "json",
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: (response) => {
            if (response.status == "success") {
                let targetStatus = response.content.status

                if(targetStatus == 'NOT_ACTIVE')
                    window.location.replace("/blogs-crm");
                else if(targetStatus == 'ACTIVE')
                    window.location.replace("/blogs-crm/pending");
            }
        },
        error: (response) => {
            let res = response.responseJSON

            console.error("changeBlogStatus", res)
        },
    });
}

function addClinicImage() {
    const file = $('#profile-settings #user-clinic-images input[type="file"]')[0].files
    let imageHtml = "";

    if (file.length > 0) {
        let t_index = addedClinicImages.length

        for (let i = 0; i < file.length; i ++) {
            imageHtml = `<div class="upload-images temp">
                            <img src="` + URL.createObjectURL(file[i]) + `" alt="Clinic Image">
                            <a href="javascript:void(0);" data-id="t${t_index}" onclick="setClinicImageDataIdToModal(this);" class="btn btn-icon btn-danger btn-sm deleteClinicImageButton" data-bs-toggle="modal" data-bs-target="#deleteClinicImageModal"><i class="far fa-trash-alt"></i></a>
                        </div>`;

            $('.clinic-section .clinic-images').append(imageHtml);

            addedClinicImages.push(file[i]);

            t_index++;
        }
    }
}

function addEducation() {
    edu_t_index ++;

    var educationcontent =  `<div class="row form-row education-cont temp">
                                <div class="col-12 col-md-10 col-lg-11">
                                    <div class="row form-row">
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>Degree</label>
                                                <input type="text" class="form-control degree" placeholder="BDS, MDS, etc.">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>College/Institute</label>
                                                <input type="text" class="form-control university">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>From</label>
                                                <input type="number" class="form-control from-date" placeholder="Example: 2018">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>To</label>
                                                <input type="number" class="form-control to-date" placeholder="Example: 2022">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2 col-lg-1 deleteEducationSection">
                                    <label class="d-md-block d-sm-none d-none">&nbsp;</label>
                                    <a href="javascript:void(0);" data-id="t${edu_t_index}" onclick="setEducationDataIdToModal(this);" class="btn btn-danger trash deleteEducationButton" data-bs-toggle="modal" data-bs-target="#deleteEducationModal">
                                        <i class="far fa-trash-alt"></i>
                                    </a>
                                </div>
                            </div>`;

    $(".education-info").append(educationcontent);
}

function addExperience() {
    exp_t_index ++;

    var experiencecontent =  `<div class="row form-row experience-cont temp">
                                <div class="col-12 col-md-10 col-lg-11">
                                    <div class="row form-row">
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>Hospital Name</label>
                                                <input type="text" class="form-control hospital-name">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>Designation</label>
                                                <input type="text" class="form-control designation">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>From</label>
                                                <input type="number" class="form-control from-date" placeholder="Example: 2018">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>To</label>
                                                <input type="number" class="form-control to-date" placeholder="Example: 2022">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2 col-lg-1 deleteExperienceSection">
                                    <label class="d-md-block d-sm-none d-none">&nbsp;</label>
                                    <a href="javascript:void(0);" data-id="t${exp_t_index}" onclick="setExperienceDataIdToModal(this);" class="btn btn-danger trash deleteExperienceButton" data-bs-toggle="modal" data-bs-target="#deleteExperienceModal">
                                        <i class="far fa-trash-alt"></i>
                                    </a>
                                </div>
                            </div>`;

    $(".experience-info").append(experiencecontent);
}

function addAward() {
    award_t_index ++;

    var awardcontent =  `<div class="row form-row awards-cont temp">
                                <div class="col-12 col-md-10 col-lg-11">
                                    <div class="row form-row">
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>Award</label>
                                                <input type="text" class="form-control award-name">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-6">
                                            <div class="form-group">
                                                <label>Year</label>
                                                <input type="number" class="form-control award-year" placeholder="Example: 2022">
                                            </div>
                                        </div>
                                        <div class="col-12 col-md-12">
                                            <div class="form-group">
                                                <label>Description</label>
                                                <textarea class="form-control award-description"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2 col-lg-1 deleteAwardSection">
                                    <label class="d-md-block d-sm-none d-none">&nbsp;</label>
                                    <a href="javascript:void(0);" data-id="t${award_t_index}" onclick="setAwardDataIdToModal(this);" class="btn btn-danger trash deleteAwardButton" data-bs-toggle="modal" data-bs-target="#deleteAwardModal">
                                        <i class="far fa-trash-alt"></i>
                                    </a>
                                </div>
                            </div>`;

    $(".awards-info").append(awardcontent);
}

function addMembership() {
    mem_t_index ++;

    var membershipcontent =  `<div class="row form-row membership-cont temp">
                                <div class="col-12 col-md-10 col-lg-11">
                                    <div class="row form-row">
                                        <div class="col-12 col-md-12">
                                            <div class="form-group">
                                                <label>Membership</label>
                                                <input type="text" class="form-control membership-name">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-2 col-lg-1 deleteMembershipSection">
                                    <label class="d-md-block d-sm-none d-none">&nbsp;</label>
                                    <a href="javascript:void(0);" data-id="t${mem_t_index}" onclick="setMembershipDataIdToModal(this);" class="btn btn-danger trash deleteMembershipButton" data-bs-toggle="modal" data-bs-target="#deleteMembershipModal">
                                        <i class="far fa-trash-alt"></i>
                                    </a>
                                </div>
                            </div>`;

    $(".membership-info").append(membershipcontent);
}

function setClinicImageDataIdToModal(identifier){
    let clinicImageId = $(identifier).attr('data-id')

    if (clinicImageId != null)
        $('#deleteClinicImageModal .modal-dialog .modal-content .modal-footer #deleteClinicImageYesButton').attr('data-id', clinicImageId)
}

function setEducationDataIdToModal(identifier){
    let educationId = $(identifier).attr('data-id')

    if (educationId != null)
        $('#deleteEducationModal .modal-dialog .modal-content .modal-footer #deleteEducationYesButton').attr('data-id', educationId)
}

function setExperienceDataIdToModal(identifier){
    let experienceId = $(identifier).attr('data-id')

    if (experienceId != null)
        $('#deleteExperienceModal .modal-dialog .modal-content .modal-footer #deleteExperienceYesButton').attr('data-id', experienceId)
}

function setAwardDataIdToModal(identifier){
    let awardId = $(identifier).attr('data-id')

    if (awardId != null)
        $('#deleteAwardModal .modal-dialog .modal-content .modal-footer #deleteAwardYesButton').attr('data-id', awardId)
}

function setMembershipDataIdToModal(identifier){
    let membershipId = $(identifier).attr('data-id')

    if (membershipId != null)
        $('#deleteMembershipModal .modal-dialog .modal-content .modal-footer #deleteMembershipYesButton').attr('data-id', membershipId)
}

function deleteClinicImage(clinicImageId) {
    if (!clinicImageId.includes('t'))
        deletedClinicImageList.push(clinicImageId)

    $(`.clinic-section .clinic-images .upload-images >a[data-id="${clinicImageId}"]`).parent().remove()
}

function deleteEducation(educationId) {
    if (!educationId.includes('t'))
        deletedEducationList.push(educationId)

    $(`.education-info .education-cont .deleteEducationSection .deleteEducationButton[data-id="${educationId}"]`).parent().parent().remove()
}

function deleteExperience(experienceId) {
    if (!experienceId.includes('t'))
        deletedExperienceList.push(experienceId)

    $(`.experience-info .experience-cont .deleteExperienceSection .deleteExperienceButton[data-id="${experienceId}"]`).parent().parent().remove()
}

function deleteAward(awardId) {
    if (!awardId.includes('t'))
        deletedAwardList.push(awardId)

    $(`.awards-info .awards-cont .deleteAwardSection .deleteAwardButton[data-id="${awardId}"]`).parent().parent().remove()
}

function deleteMembership(membershipId) {
    if (!membershipId.includes('t'))
        deletedMembershipList.push(membershipId)

    $(`.membership-info .membership-cont .deleteMembershipSection .deleteMembershipButton[data-id="${membershipId}"]`).parent().parent().remove()
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

function hideErrors() {
    let jQuerySelector = `#profile-settings .form-error`

    // remove error
    $(jQuerySelector).removeClass('form-error')
    $(`#profile-settings .text-danger`).remove()
}

function showErrors(field, message) {
    let jQuerySelector = `#profile-settings #user-${field}`

    // show error
    $(jQuerySelector).addClass('form-error')
    $(jQuerySelector).append(`<p class="text-danger error-user-${field}">${message}</p>`)
}

function getProfileData() {
    // define query selector
    let profilePhotoSelector = '#profile-settings #user-profile-photo input[type="file"]'
    let nameSelector = '#profile-settings #user-name input[type="text"]'
    let emailSelector = '#profile-settings #user-email input[type="email"]'
    let phoneSelector = '#profile-settings #user-phone-number input[type="number"]'
    let genderSelector = '#profile-settings #user-gender #gender'
    let birtDateSelector = '#profile-settings #user-birth-date input[type="date"]'
    let biographySelector = '#profile-settings #user-biography textarea'
    let clinicNameSelector = '#profile-settings #user-clinic-name input[type="text"]'
    let clinicAddressSelector = '#profile-settings #user-clinic-address input[type="text"]'
    let clinicImagesSelector = '#profile-settings #user-clinic-images input[type="file"]'
    let contactCitySelector = '#profile-settings #user-contact-city input[type="text"]'
    let contactStateSelector = '#profile-settings #user-contact-state input[type="text"]'
    let contactCountrySelector = '#profile-settings #user-contact-country input[type="text"]'
    let contactPostalCodeSelector = '#profile-settings #user-contact-postal-code input[type="text"]'
    let contactAddressLineSelector = '#profile-settings #user-contact-address-line input[type="text"]'
    let serviceSelector = '#profile-settings #user-service input[type="text"]'
    let specialistSelector = '#profile-settings #user-specialist input[type="text"]'


    // disable input and button
    let affectedItemList = []

    affectedItemList.push(profilePhotoSelector)
    affectedItemList.push(nameSelector)
    affectedItemList.push(emailSelector)
    affectedItemList.push(phoneSelector)
    affectedItemList.push(genderSelector)
    affectedItemList.push(birtDateSelector)
    affectedItemList.push(biographySelector)
    affectedItemList.push(clinicNameSelector)
    affectedItemList.push(clinicAddressSelector)
    affectedItemList.push(clinicImagesSelector)
    affectedItemList.push(contactCitySelector)
    affectedItemList.push(contactStateSelector)
    affectedItemList.push(contactCountrySelector)
    affectedItemList.push(contactPostalCodeSelector)
    affectedItemList.push(contactAddressLineSelector)
    affectedItemList.push(serviceSelector)
    affectedItemList.push(specialistSelector)
    affectedItemList.push('#profile-settings .clinic-images .deleteClinicImageButton')
    affectedItemList.push('#profile-settings .submit-section button[name="form_submit"]')

    disableItems(affectedItemList)


    // get data
    let profilePhoto = $(profilePhotoSelector)[0].files
    let name = $(nameSelector).val()
    let email = $(emailSelector).val()
    let phone = $(phoneSelector).val()
    let gender = $(genderSelector).val()
    let birthDate = $(birtDateSelector).val()
    let biography = $(biographySelector).val()
    let clinicName = $(clinicNameSelector).val()
    let clinicAddress = $(clinicAddressSelector).val()
    let contactCity = $(contactCitySelector).val()
    let contactState = $(contactStateSelector).val()
    let contactCountry = $(contactCountrySelector).val()
    let contactPostalCode = $(contactPostalCodeSelector).val()
    let contactAddressLine = $(contactAddressLineSelector).val()
    let service = $(serviceSelector)[1].value
    let specialist = $(specialistSelector)[1].value


    // ********** form data (begin) ********** //
    let formData = new FormData();

    // profile photo
    if (profilePhoto.length > 0)
        formData.append("image", profilePhoto[0]);
    else
        formData.append("image", new File([""], "filename.txt", {type: "text/plain", lastModified: null}))

    formData.append("name", name);
    formData.append("email", email);
    formData.append("phone", phone);
    formData.append("gender", gender);
    formData.append("dateOfBirth", birthDate);
    formData.append("biography", biography);
    formData.append("clinicName", clinicName);
    formData.append("clinicAddress", clinicAddress);

    // added clinic image list
    if (addedClinicImages.length > 0) {
        let index = 0;
        $('#profile-settings .clinic-images .upload-images.temp .deleteClinicImageButton').each(function () {
            let clinicImageId = $(this).attr('data-id');
            let indexTemp = clinicImageId.replace('t', '')

            formData.append(`clinicImages[${index}]`, addedClinicImages[indexTemp]);

            index++;
        })
    }
    else
        formData.append("clinicImages[0]", new File([""], "filename.txt", {type: "text/plain", lastModified: null}))

    // deleted clinic image list
    if (deletedClinicImageList.length > 0) {
        for (let k = 0; k < deletedClinicImageList.length; k++) {
            formData.append(`deletedClinicImageList[${k}]`, parseInt(deletedClinicImageList[k]));
        }
    }
    else
        formData.append("deletedClinicImageList[0]", -1)

    // added education list
    let edu_t_count = 0;
    $('#profile-settings .education-info .education-cont').each(function () {
        let id = $(this).find('.deleteEducationButton').attr('data-id')
        let degree = $(this).find('.degree').val()
        let university = $(this).find('.university').val()
        let fromDate = $(this).find('.from-date').val()
        let toDate = $(this).find('.to-date').val()

        formData.append(`educationList[${edu_t_count}].id`, id.includes('t') ? -1 : parseInt(id))
        formData.append(`educationList[${edu_t_count}].degree`, degree)
        formData.append(`educationList[${edu_t_count}].university`, university)
        formData.append(`educationList[${edu_t_count}].fromDate`, fromDate.trim())
        formData.append(`educationList[${edu_t_count}].toDate`, toDate.trim())

        edu_t_count ++;
    })

    // deleted education list
    if (deletedEducationList.length > 0) {
        for (let k = 0; k < deletedEducationList.length; k++) {
            formData.append(`deletedEducationList[${k}]`, parseInt(deletedEducationList[k]));
        }
    }
    else
        formData.append("deletedEducationList[0]", -1)

    // added experience list
    let exp_t_count = 0;
    $('#profile-settings .experience-info .experience-cont').each(function () {
        let id = $(this).find('.deleteExperienceButton').attr('data-id')
        let hospitalName = $(this).find('.hospital-name').val()
        let designation = $(this).find('.designation').val()
        let fromDate = $(this).find('.from-date').val()
        let toDate = $(this).find('.to-date').val()

        formData.append(`experienceList[${exp_t_count}].id`, id.includes('t') ? -1 : parseInt(id))
        formData.append(`experienceList[${exp_t_count}].hospitalName`, hospitalName)
        formData.append(`experienceList[${exp_t_count}].designation`, designation)
        formData.append(`experienceList[${exp_t_count}].fromDate`, fromDate.trim())
        formData.append(`experienceList[${exp_t_count}].toDate`, toDate.trim())

        exp_t_count ++;
    })

    // deleted experience list
    if (deletedExperienceList.length > 0) {
        for (let k = 0; k < deletedExperienceList.length; k++) {
            formData.append(`deletedExperienceList[${k}]`, parseInt(deletedExperienceList[k]));
        }
    }
    else
        formData.append("deletedExperienceList[0]", -1)

    // added award list
    let award_t_count = 0;
    $('#profile-settings .awards-info .awards-cont').each(function () {
        let id = $(this).find('.deleteAwardButton').attr('data-id')
        let name = $(this).find('.award-name').val()
        let year = $(this).find('.award-year').val()
        let description = $(this).find('.award-description').val()

        formData.append(`awardList[${award_t_count}].id`, id.includes('t') ? -1 : parseInt(id))
        formData.append(`awardList[${award_t_count}].name`, name)
        formData.append(`awardList[${award_t_count}].year`, year.trim())
        formData.append(`awardList[${award_t_count}].description`, description)

        award_t_count ++;
    })

    // deleted award list
    if (deletedAwardList.length > 0) {
        for (let k = 0; k < deletedAwardList.length; k++) {
            formData.append(`deletedAwardList[${k}]`, parseInt(deletedAwardList[k]));
        }
    }
    else
        formData.append("deletedAwardList[0]", -1)

    // added membership list
    let mem_t_count = 0;
    $('#profile-settings .membership-info .membership-cont').each(function () {
        let id = $(this).find('.deleteMembershipButton').attr('data-id')
        let name = $(this).find('.membership-name').val()

        formData.append(`membershipList[${mem_t_count}].id`, id.includes('t') ? -1 : parseInt(id))
        formData.append(`membershipList[${mem_t_count}].name`, name)

        mem_t_count ++;
    })

    // deleted membership list
    if (deletedMembershipList.length > 0) {
        for (let k = 0; k < deletedMembershipList.length; k++) {
            formData.append(`deletedMembershipList[${k}]`, parseInt(deletedMembershipList[k]));
        }
    }
    else
        formData.append("deletedMembershipList[0]", -1)
    
    formData.append("city", contactCity);
    formData.append("state", contactState);
    formData.append("country", contactCountry);
    formData.append("postalCode", contactPostalCode);
    formData.append("addressLine", contactAddressLine);
    formData.append("service", service);
    formData.append("specialist", specialist);
    // ********** form data (end) ********** //


    return {
        affectedItemList,
        formData
    };
}

async function updateProfile() {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let resultObject = getProfileData();
    let formData = resultObject.formData;
    let affectedItemList = resultObject.affectedItemList;

    let response = await fetch($('#profile-settings').attr('action'), {
        headers: {
            'X-CSRF-Token': token,
            'X-CSRF-HEADER': header
        },
        method: $('#profile-settings').attr('method'),
        enctype: 'multipart/form-data',
        body: formData
    });

    let res = await response.json()

    if (res.status === 'success') { // success
        enableItems(affectedItemList)

        // hide errors
        hideErrors()

        // show success message (begin)
        $('#profile-settings #profile-success-message').remove()

        let message = '<div id="profile-success-message">\n' +
                            '<div class="alert alert-info">You have successfully updated profile!</div>\n' +
                        '</div>';

        $(message).insertBefore($('#profile-settings #basic-information-title'));

        $("html, body").animate({ scrollTop: 0 }, "slow");
        // show success message (end)
    }
    else { // error
        if (res.errors != null) {
            for (let j = 0; j < res.errors.length; j++) {
                hideErrors()

                showErrors(
                    res.errors[j].field,
                    res.errors[j].defaultMessage
                )
            }

        }

        let formErrorArea = `#profile-settings .form-error`;
        let headerHeight = $('.header').height()
        let position = $(formErrorArea).offset().top - headerHeight;
        $("html, body").animate({ scrollTop: position }, "slow");

        enableItems(affectedItemList)
    }
}


