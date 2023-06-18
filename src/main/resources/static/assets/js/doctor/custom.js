//import 'function'

// preview blog image (begin)
$('.content .container-fluid .doctor-add-blog .service-fields .service-upload #image').on('change', function () {
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