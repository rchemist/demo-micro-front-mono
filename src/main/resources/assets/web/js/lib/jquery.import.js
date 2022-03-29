(function () {
    let codeview = new Array();
    codeview.push({ 'url': '../../../assets/web/js/lib/jquery-1.11.1.min.js', 'cashbuster': false });
    codeview.push({ 'url': '../../../assets/web/js/lib/ripples.js', 'cashbuster': false });
    codeview.push({ 'url': '../../../assets/web/js/lib/slick.min.js', 'cashbuster': false });
    codeview.push({ 'url': '../../../assets/web/js/lib/lottie.min.js', 'cashbuster': false });
    codeview.push({ 'url': '../../../assets/web/js/lib/jquery.selectric.js', 'cashbuster': false });
    codeview.push({ 'url': '../../../assets/web/js/lib/video.min.js', 'cashbuster': false });
    codeview.push({ 'url': '../../../assets/web/js/ui.common.js', 'cashbuster': true });

    for (let a = 0, atotal = codeview.length; a < atotal; a++) {
        document.write('<script src="' + codeview[a].url + ((codeview[a].cashbuster) ? '?cb=' + new Date().getTime() : '') + '" charset="utf-8"></' + 'script>');
    };
})();
