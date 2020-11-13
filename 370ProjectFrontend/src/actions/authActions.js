import axios from 'axios'

// var config = {
//     headers: {'Access-Control-Allow-Origin': '*'}
// };

const apiUrl = 'http://localhost:8080'

export const logout=()=>{
    return {type:'LOGOUT_SUCCESS'}
}


export const login=(Users)=>{
    console.log(Users)
    return (dispatch) =>{
        return axios.post(`${apiUrl}/login`,Users)
        .then(response=>{
            if(response.data === ""){
                dispatch({type:'LOGIN_ERROR'})
            }
            else{
                dispatch(loginSuccess(response.data))
            }
        })
        .catch(erro =>{
            dispatch({type:'LOGIN_ERROR'})
        })
    }
    }
    
export const loginSuccess=(data)=>{
    return {
        type:'LOGIN_SUCCESS',
        payload: data
    }
}
export const signup=(newUser)=>{
    console.log(newUser);
    return (dispatch) =>{
        return axios.post(`${apiUrl}/signup`,newUser)
        .then(response=>{
            dispatch(signupSuccess(response.data))
        })
        .catch(erro =>{
            dispatch({type:'SIGNUP_ERROR'})
        })
    }

}

export const signupSuccess=(data)=>{
    return {
        type:'SIGNUP_SUCCESS',
        payload: data
    }
}

export const edit_profile=(newProfile)=>{
    console.log(newProfile);
    return (dispatch) =>{
        return axios.post(`${apiUrl}/editprofile`,newProfile)
        .then(response=>{
            dispatch(editProfileSuccess(response.data))
        })
        .catch(erro =>{
            dispatch({type:'EDITPROFILE_ERROR'})
        })
    }
    
}

export const editProfileSuccess=(data)=>{
    return {
        type:'EDITPROFILE_SUCCESS',
        payload: data
    }
}
