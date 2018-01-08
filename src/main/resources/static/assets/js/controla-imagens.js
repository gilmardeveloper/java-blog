var imgs = document.querySelectorAll('img[data-src]');

imgs[0].src = imgs[0].getAttribute('data-src');
setTimeout(function(){imgs[1].src = imgs[1].getAttribute('data-src');}, 600);
var isScrolling = false;

window.onscroll = function() {
	console.log('Scroll', scrollY);
	if (isScrolling) return;
    isScrolling =true;
    setTimeout(function(){
        isScrolling = false;
    }, 100);

	for (var i = 2; i < imgs.length; i++) {

		if (imgs[i].getBoundingClientRect().top < window.innerHeight + 200) { 
			imgs[i].src = imgs[i].getAttribute('data-src');

		}
	}
};