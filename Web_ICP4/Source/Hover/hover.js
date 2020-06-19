function upDate(previewPic) {
    /* changing the background image to preview image on which mouse is hovered */
    $('#image').css('background-image', 'url(' + previewPic.src + ')');
    /* changing the text of the empty frame to text of the corresponding preview image */
    $('#image').html(previewPic.alt);
}
function unDo() {
    /* changing the background image to none when the mouse is removed from preview image */
    $('#image').css('background-image','none');
    /* changing the text to default when the mouse is removed from preview image */
    $('#image').html("Hover over an image below to display here.");
}