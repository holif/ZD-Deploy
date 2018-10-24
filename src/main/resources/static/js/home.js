
var data = {};
var currentproject='';
var currentversion='';
var currentfile='';
//项目视图
var projectview = Vue.component('project-view',{

    template:'<div class="btn-group">' +
    '<button v-for="project in items" v-on:click="listversion(project)" type="button" style="margin:10px;width:120px;height:40px" class="btn btn-default">' +
    ' {{project}} </button>  </div>',

    methods:{
        listversion:function(project){
            //$(".loading").fadeIn();
            currentproject = project;
            vm.currentproject = project;
            axios.get('/app/'+project).then(function(response){

                data.items = response.data;
                vm.currentview = 'version-view';
            })
        }
    },
    data:function(){
        return data;
    }
});
//版本视图
var versionview =Vue.component( 'version-view',{
    template:'<div class="btn-group" id="versionView">' +
    '<div v-for="versionInfo in items"  style="float:left"><button v-on:click="listfile(versionInfo.version)" type="button" style="margin:10px;width:120px;height:40px" class="btn btn-default">' +
    ' {{versionInfo.version}} </button> <p>{{ moment(versionInfo.modifyTime).format("MM-DD HH:mm") }}</p></div> </div>',
    methods:{
        listfile:function(version){
            vm.currentversion = version;
            currentversion = version;
            axios.get('/app/'+currentproject+'/'+version).then(function(response){
                data.items=[];
                response.data.forEach(function(file){
                    data.items.push(file.fileName);
                });
                vm.currentview = 'file-view';
            })
        }
    },
    data:function(){
        return data;
    }
});
//文件视图
var fileview =Vue.component( 'file-view',{
    template:'<div class="btn-group">' +
    '<button v-for="file in items" v-on:click="deploy(file)" type="button" style="margin:10px;width:170px;height:40px" class="btn btn-default" data-toggle="modal" data-target="#myModal">' +
    ' {{file}} </button>  </div>',
    methods:{
        deploy:function(file){
            currentfile = file;
            vm.currentfile = file;

        }
    },
    data:function(){
        return data;
    }
});

//构造vue根实例
var vm = new Vue({
    el: "#deployapp",
    data: {
        currentview:'',
        seen0:true,
        seen1:false,
        currentproject:'',
        currentversion:'',
        currentfile:'',
        name:'',
        details:'',
        deployresult:'',
    },

    methods:{
        listProject:function(){
            var sef = this;
            this.seen0 = false;
            this.seen1 = true;
            axios.get('/app/names').then(function(response){
                data.items = response.data;
                sef.currentview = 'project-view';
            })
        },
        saveRecord:function () {
            var sef = this;
            if(sef.name==null||sef.name==""){
                alert("用户名不能为空");
                return;
            }
            if(sef.details==null||sef.details==""){
                alert("部署说明不能为空");
                return;
            }
            $('#myModal').modal('hide');
            $('#loading').modal('show');
            var params = new URLSearchParams();
            params.append("username", sef.name);
            params.append("desc", sef.details);
            axios.post(
                '/app/' + currentproject + '/' + currentversion + '/' + currentfile + '/',
                params
            ).then(function(response) {
                sef.deployresult = "Deploy Success!";
                $('#loading').modal('hide');
                $('#modalResult').modal('show');

            }).catch(function (error) {
                sef.deployresult = "Deploy Failed !     Error Reason: "+error.data.error;
                console.log(error);
                $('#loading').modal('hide');
                $('#modalResult').modal('show');
            });
        }
    }
});
