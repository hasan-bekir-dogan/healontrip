//import 'function'

// preview blog image (begin)
$('.doctor-add-blog .service-fields .service-upload #image').on('change', function () {
    previewBlogImage()
});
// preview blog image (end)

// activate blog (begin)
$('.activateBlogButton').on('click', function () {
    let blogId = $(this).attr('data-id')
    $('#activateBlogModal .modal-dialog .modal-content .modal-footer #activateBlogYesButton').attr('data-id', blogId)
})
$('#activateBlogModal .modal-dialog .modal-content .modal-footer #activateBlogYesButton').on('click', function () {
    let blogId = $(this).attr('data-id')
    changeBlogStatus(blogId, 'ACTIVE')
})
// activate blog (end)

// inactivate blog (begin)
$('.inactivateBlogButton').on('click', function () {
    let blogId = $(this).attr('data-id')
    $('#inactivateBlogModal .modal-dialog .modal-content .modal-footer #inactivateBlogYesButton').attr('data-id', blogId)
})
$('#inactivateBlogModal .modal-dialog .modal-content .modal-footer #inactivateBlogYesButton').on('click', function () {
    let blogId = $(this).attr('data-id')
    changeBlogStatus(blogId, 'NOT_ACTIVE')
})
// inactivate blog (end)

// preview profile image
$('#profile-settings #user-profile-photo input[type="file"]').on('change', function () {
    previewProfileImage()
});

// add clinic image
$('#profile-settings #user-clinic-images input[type="file"]').on('change', function () {
    addClinicImage();
    return false;
});

// check year number
/*$('.year-input').on("change", function() {
    let v = parseInt(this.value);
    let currentDate = new Date().getFullYear();

    if (v < 1900)
        this.value = 1900;

    if (v > currentDate)
        this.value = currentDate;
});*/

// education add more
$(".add-education").on('click', function () {
    addEducation();
    return false;
});

// experience add more
$(".add-experience").on('click', function () {
    addExperience();
    return false;
});

// award add more
$(".add-award").on('click', function () {
    addAward();
    return false;
});

// membership add more
$(".add-membership").on('click', function () {
    addMembership();
    return false;
});

// delete clinic image
$('#deleteClinicImageModal .modal-dialog .modal-content .modal-footer #deleteClinicImageYesButton').on('click', function () {
    let clinicImageId = $(this).attr('data-id')

    if (clinicImageId != null) {
        $('#deleteClinicImageModal').modal('hide')

        deleteClinicImage(clinicImageId)
    }
})

// delete education
$('#deleteEducationModal .modal-dialog .modal-content .modal-footer #deleteEducationYesButton').on('click', function () {
    let educationId = $(this).attr('data-id')

    if (educationId != null) {
        $('#deleteEducationModal').modal('hide')

        deleteEducation(educationId)
    }
})

// delete experience
$('#deleteExperienceModal .modal-dialog .modal-content .modal-footer #deleteExperienceYesButton').on('click', function () {
    let experienceId = $(this).attr('data-id')

    if (experienceId != null) {
        $('#deleteExperienceModal').modal('hide')

        deleteExperience(experienceId)
    }
})

// delete award
$('#deleteAwardModal .modal-dialog .modal-content .modal-footer #deleteAwardYesButton').on('click', function () {
    let awardId = $(this).attr('data-id')

    if (awardId != null) {
        $('#deleteAwardModal').modal('hide')

        deleteAward(awardId)
    }
})

// delete membership
$('#deleteMembershipModal .modal-dialog .modal-content .modal-footer #deleteMembershipYesButton').on('click', function () {
    let membershipId = $(this).attr('data-id')

    if (membershipId != null) {
        $('#deleteMembershipModal').modal('hide')

        deleteMembership(membershipId)
    }
})

// update profile
$('#profile-settings').on('submit', function (e) {
    e.preventDefault();

    updateProfile()
})

// crop and upload profile photo
$('#profile-settings #user-profile-photo .upload-img .change-photo-btn2 input[type="file"]').on('change', function (e) {
    $('#changeProfilePhotoModal .modal-body').html('<div id="overlay">\n' +
        '                                                   <div class="cv-spinner">\n' +
        '                                                       <div class="spinner">\n' +
        '                                                       </div>\n' +
        '                                                   </div>\n' +
        '                                                 </div>')
    $("#overlay").fadeIn(300);
    $('#changeProfilePhotoModal').modal('show')

    setTimeout(function () {
        const files = e.target.files;
        const done = (url) => {
            e.target.value = '';

            $("#overlay").fadeOut(300);

            $('#changeProfilePhotoModal .modal-body').html('<div class="crop-frame">\n' +
                '                                                     <img id="profilePhotoCropPreview" src="' + url + '" style="max-width: 100%; min-width: 100%; max-height: 100%; min-height: 100%;">\n' +
                '                                                 </div>')

            cropperProfilePhoto = new Cropper(document.getElementById('profilePhotoCropPreview'), {
                aspectRatio: 1,
                viewMode: 3,
                preview: '.preview',
            });
        };
        if (files && files.length > 0) {
            const reader = new FileReader();
            reader.onload = (e) => {
                done(reader.result);
            };
            reader.readAsDataURL(files[0]);
        }
    },200)
});

$('#changeProfilePhotoModal .modal-footer #changeProfilePhotoYesButton').on('click', function () {
    cropperProfilePhoto.getCroppedCanvas().toBlob((blob) => {
        profilePhotoFormData.delete("image")
        profilePhotoFormData.append('image', blob);

        $('#changeProfilePhotoModal').modal('hide')
    });
    var uri = cropperProfilePhoto.getCroppedCanvas().toDataURL("image/jpeg", 0.7);
    previewProfileImage(uri)
});


