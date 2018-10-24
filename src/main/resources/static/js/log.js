var log = new Vue({
    el: '#logtable',
    data: {
        logs: []
    },
    created: function(){
        var vm = this;
        axios.get('/log/list')
            .then(function (response) {
                vm.logs = response.data;
            })
            .catch(function (error) {
                console.log("error")
            });
    }
});