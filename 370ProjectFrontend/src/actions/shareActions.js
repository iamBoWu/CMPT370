import axios from 'axios'

const apiUrl = 'http://localhost:8080'

export const getShare =(user_id)=>{
    return (dispatch) => {
        return axios.get(`${apiUrl}/showAllShareEvent/${user_id}`)
        .then(response=>{
            dispatch({type:'GETALL_SHARE',payload:response.data})
        })
        .catch(erro =>{
            dispatch({type:'GETALLSHARE_FAIL'})
        })
      }
}

export const deleteShare =(shareUserId, eventId)=>{
    return (dispatch) => {
        return axios.put(`${apiUrl}/deleteShare`, {
            shareUserId: shareUserId,
            eventId: eventId
        })
        .then(()=>{
            dispatch({ type: 'DELETE_SHARE' })
        }).catch(err=>{
            dispatch({type:'DELETESHARE_FAIL'})
        })
        
      }
}

export const addNewShare =(share)=>{
    return (dispatch) =>{
        return axios.post(`${apiUrl}/addNewShare`,share)
        .then(()=>{
            dispatch({type:'ADDSHARE_SUCCESS',share})
        })
        .catch(erro =>{
            dispatch({type:'ADDSHARE_FAIL'})
        })
    }

}