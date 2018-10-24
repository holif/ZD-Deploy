/**
 * Created by XuZhipeng on 2016/12/13.
 */

var data = {};
var currentproject='';
var currentversion='';
var currentfile='';
///////////////////////////////////////////////////////////////////////////////////展示项目的视图
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////展示版本的视图
var versionview =Vue.component( 'version-view',{
    template:'<div class="btn-group" id="versionView">' +
    '<div v-for="versionInfo in items"  style="float:left"><button v-on:click="listfile(versionInfo.version)" type="button" style="margin:10px;width:120px;height:40px" class="btn btn-default">' +
    ' {{versionInfo.version}} </button> <p>{{ moment(versionInfo.modifyTime).format("MM-DD HH:mm") }}</p></div> </div>',
    methods:{
        listfile:function(version){
            //$(".loading").fadeOut();
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////展示文件的视图
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

////////////////////////////////////////////////////////////////////////////////////////////////////////构造vue根实例
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
                axios.get('/app').then(function(response){
                    data.items = response.data;
                    sef.currentview = 'project-view';
                })
            },
            saveRecord:function () {
                $('#myModal').modal('hide');
               // $('#loadingcop').show();
                $("#loadingcop").css("display","block")
                var sef = this;
                var params = new URLSearchParams();
                params.append("username", sef.name);
                params.append("desc", sef.details);
                axios.post(
                    '/app/' + currentproject + '/' + currentversion + '/' + currentfile + '/',
            	    params
                ).then(function(response) {
                    sef.deployresult = "Deploy Success!";
                    //$('#loadingcop').hide();
                    $("#loadingcop").css("display","none")
                    $('#modalResult').modal('show');

                }).catch(function (error) {
                    sef.deployresult = "Deploy Failed !     Error Reason: "+error.data.error;
                    console.log(error);
                   // $('#loadingcop').hide();
                    $("#loadingcop").css("display","none")
                    $('#modalResult').modal('show');

                });
            }
        }
    });


////////////////////////////////////////////////////////////////////////////////////////////////////////构造vue根实例
var listappvm = new Vue({
    el: "#listapp",
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
            axios.get('/app').then(function(response){
                data.items = response.data;
                sef.currentview = 'project-view';
            })
        },
        saveRecord:function () {
            $('#myModal').modal('hide');
            // $('#loadingcop').show();
            $("#loadingcop").css("display","block")
            var sef = this;
            axios.post(
                '/apps'
            ).then(function(response) {
                sef.deployresult = "Deploy Success!";
                //$('#loadingcop').hide();
                $("#loadingcop").css("display","none")
                $('#modalResult').modal('show');

            }).catch(function (error) {
                sef.deployresult = "Deploy Failed !     Error Reason: "+error.data.error;
                console.log(error);
                // $('#loadingcop').hide();
                $("#loadingcop").css("display","none")
                $('#modalResult').modal('show');

            });
        }
    }
});

