var data = {};
data.users=[];

var  userview = Vue.component('userConfig-view',{

    template:' <table class="table table-bordered"> <thead>  <th style="text-align: center">用户名</th>' +
    ' <th style="text-align: center">密码</th><th style="text-align: center">邮箱</th>' +
    ' <th style="text-align: center">操作</th> </thead><tbody><tr v-for="user in users"><td>{{user.username}}</td>' +
    '<td>{{user.password}}</td><td>{{user.email}}</td><td><a  v-on:click="disableUser(user.username)"  href="#">禁用</a></td></tr></tbody></table>',
    methods:{
        disableUser:function(username){

            axios.get('/user/disable/'+username)
        }
    },
    data:function(){
        return data;
    }
});


var profileview = Vue.component('profileConfig-view',{

    template:' <div class="list-group">    <a href="#" class="list-group-item active">    OPTIONS    </a> ' +
    '   <a href="/goModify" class="list-group-item">modify password</a>' +
    '<a href="/register" class="list-group-item">register new account</a></div>',

});

var projectview = Vue.component('projectConfig-view',{

    template:' <div class="list-group">    <a href="#" class="list-group-item active">    OPTIONS    </a> ' +
    '   <a href="/listDeploy" class="list-group-item">List Deploy</a>' +
    '   <a href="/listNode" class="list-group-item">List Node</a>' +
    '<a href="/listApp" class="list-group-item">List App</a></div>',

});

var noauthView = Vue.component('noAuth-view',{

    template:'<h3>抱歉你没有该权限进行此操作！</h3>',

})

var vm = new Vue({

    el:"#configvm",
    data:{
        currentview:'profileConfig-view',
    },
    methods:{
        userConfig:function(){
            var sef = this;

            axios.get("/user/findAuthority").then(function(response){

                 var  result = response.data;
                alert(result);
                if(result=="ADMIN"){

                    axios.get('/user/findAll').then(function(response) {
                        data.users=  response.data;
                    })
                    sef.currentview = "userConfig-view"

                }else{
                    sef.currentview = "noAuth-view"
                }

            })



        },
        projectConfig:function(){
            this.currentview = "projectConfig-view"
        },
        profileConfig:function(){

            this.currentview = "profileConfig-view"
        }
    }

})