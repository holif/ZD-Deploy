var node = new Vue({
    el: '#nodetable',
    data: {
        nodes: []
    },
    created: function(){
        var vm = this;
        axios.get('/node/list')
            .then(function (response) {
                vm.nodes = response.data;
            })
            .catch(function (error) {
                console.log("error")
            });
    }
});