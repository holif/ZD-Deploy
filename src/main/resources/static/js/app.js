var app = new Vue({
    el: '#apptable',
    data: {
        apptables: []
    },
    created: function(){
        var vm = this;
        axios.get('/app/list')
            .then(function (response) {
                vm.apptables = response.data;
                console.log(vm.apptables)
            })
            .catch(function (error) {
                console.log("error")
            });
    }
});