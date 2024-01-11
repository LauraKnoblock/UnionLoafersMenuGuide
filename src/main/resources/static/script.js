document.addEventListener('DOMContentLoaded', function() {
    window.onscroll = function() { scrollFunction() };

    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            document.querySelector('.back-to-top').style.display = 'block';
        } else {
            document.querySelector('.back-to-top').style.display = 'none';
        }
    }

    document.querySelector('.back-to-top').addEventListener('click', function() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
    });
});
