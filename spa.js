const app = {
    pages: [],
    show: new Event('show'),
    init: function(){
        app.pages = document.querySelectorAll('.page');
        app.pages.forEach((pg)=>{
            pg.addEventListener('show', app.pageShown);
        })

        document.querySelectorAll('.nav-link').forEach((link)=>{
            link.addEventListener('click', app.nav);
        })
        history.replaceState({}, 'homepage', '#homepage');
        window.addEventListener('popstate', app.poppin);
    },
    nav: function(ev){
        ev.preventDefault();
        let currentPage = ev.target.getAttribute('redirect-target');
        document.querySelector('.activated').classList.remove('activated');
        document.getElementById(currentPage).classList.add('activated');
        //alert(currentPage);
        //console.log(currentPage);
        history.pushState({}, currentPage, `#${currentPage}`);
        document.getElementById(currentPage).dispatchEvent(app.show);
    },
    pageShown: function(ev){
        //console.log('Page', ev.target.id, 'just shown');
    },
    poppin: function(ev){
        //console.log(location.hash, 'popstate event');
        let hash = location.hash.replace('#' ,'');
        document.querySelector('.activated').classList.remove('activated');
        document.getElementById(hash).classList.add('activated');
        //console.log(hash);
        //history.pushState({}, currentPage, `#${currentPage}`);
        document.getElementById(hash).dispatchEvent(app.show);
      }
}

document.addEventListener('DOMContentLoaded', app.init);
