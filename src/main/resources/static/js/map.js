var map = new Vue({
    el: '#maptable',
    data: {
        maps: []
    },
    created: function(){
        var vm = this;
        axios.get('/appnode/list')
            .then(function (response) {
                vm.maps = response.data;
            })
            .catch(function (error) {
                console.log("error")
            });
    }
});