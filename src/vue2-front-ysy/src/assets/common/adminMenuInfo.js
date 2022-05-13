import axios from 'axios'

export default{
    getAdminMenus: ()=>{
        // axios.get("/user/menuList")
        //then(res=>{
            adminMenu = res;
            var menuJson = {
                
            }
        res.forEach(x => {
            menuJson.path = x.path;
            menuJson.name = x.name;
            menuJson.component = ()=>{};
            this.adminMenus.push(menuJson) ;
        });
        // })
        // return res;


    },
    adminMenus : []

}