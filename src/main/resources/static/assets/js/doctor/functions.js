
function previewBlogImage() {
    const [file] = $('.content .container-fluid .doctor-add-blog .service-fields .service-upload #image')[0].files
    let imageHtml = "";

    if (file) {
        let imageExistyn = $('.content .container-fluid .doctor-add-blog .service-fields #uploadPreview .upload-wrap .upload-images').html();

        if (imageExistyn !== '' && imageExistyn !== undefined) {
            console.log('if')
            $('.content .container-fluid .doctor-add-blog .service-fields #uploadPreview .upload-wrap .upload-images >img').attr('src', URL.createObjectURL(file));
        }
        else {
            console.log('else')
            imageHtml = `<ul class="upload-wrap">
                            <li>
                                <div class="upload-images">
                                    <img alt="Blog Image" src="` + URL.createObjectURL(file) + `">
                                </div>
                            </li>
                        </ul>`;

            $('.content .container-fluid .doctor-add-blog .service-fields #uploadPreview').html(imageHtml);
        }
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
        url: "/doctor/blogs/change-blog-status",
        method: "POST",
        dataType: "json",
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: (response) => {
            if (response.status == "success") {
                let targetStatus = response.content.status

                if(targetStatus == 'NOT_ACTIVE')
                    window.location.replace("/doctor/blogs");
                else if(targetStatus == 'ACTIVE')
                    window.location.replace("/doctor/blogs/pending");
            }
        },
        error: (response) => {
            let res = response.responseJSON

            if (res.status == 'fail') {
                console.error("changeBlogStatus", res)
            }
        },
    });
}
