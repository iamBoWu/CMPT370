const initState = {
    user:{},
    authError: null,
    isLogin: null
}

const authReducer = (state=initState,action) =>{
    switch(action.type){
        case 'LOGIN_ERROR':
          console.log('login error');
          return {
            ...state,
            authError: 'Login failed'
          }
        case 'LOGIN_SUCCESS':
          console.log('login success');
          console.log(action.payload);
          return {
            ...state,
            user:action.payload,
            authError: null,
            isLogin: 'yes',
          }
        case 'LOGOUT_SUCCESS':
          console.log('logout success');
          return {
            ...state,
            authError: null,
            isLogin: null,
          }
        case 'SIGNUP_SUCCESS':
            console.log('signup success');
            return{
                ...state,
                user:action.payload,
                authError: null,
                isLogin: 'yes',
            }
        case 'SIGNUP_ERROR':
            console.log('signup failed');
            return {
                ...state,
                authError: 'Signup failed'
              }
        case 'EDITPROFILE_SUCCESS':
            console.log('edit profile success');
            return{
                ...state,
                user:action.payload,
                authError: null,
                isLogin: 'yes'
            }
        default:
          return state
      }
}

export default authReducer;