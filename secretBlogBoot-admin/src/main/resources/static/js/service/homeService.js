homeApp.service('homeService',function($http){
    this.getNewBlogTitle=function(rows){
        return $http.get('../blog/getNewBlogTitle?rows='+rows);
    }

    this.getSysParameter=function(){
        return $http.get('/sys/getSysParameter');
    }
});
