import axios from 'axios'

const apiUrl = 'http://localhost:8080'

export const getEvent =(user_id)=>{
    return (dispatch) => {
        return axios.get(`${apiUrl}/showAllevent/${user_id}`)
        .then(response=>{
            dispatch({type:'GETALL_EVENT',payload:response.data})
        })
        .catch(erro =>{
            dispatch({type:'GETALLEVENT_FAIL'})
        })
      }
}


export const deleteEvent =(eventId)=>{
    return (dispatch) => {
        return axios.delete(`${apiUrl}/deleteEvent/${eventId}`)
        .then(()=>{
            dispatch({ type: 'DELETE_EVENT', eventId })
        }).catch(err=>{
            dispatch({type:'DELETEEVENT_FAIL'})
        })
        
      }
}

// export const updateEvent =(newEvent)=>{
//     console.log(newEvent);
//     return (dispatch) =>{
//         return axios.put(`${apiUrl}/updateEvent`,newEvent)
//         .then(()=>{
//             dispatch({type:'UPDATEEVENT_SUCCESS'})
//         })
//         .catch(erro =>{
//             dispatch({type:'UPDATEEVENT_FAIL'})
//         })
//     }

// }

export const updateEvent =(newEvent)=>{
    console.log(newEvent);
    return (dispatch) =>{
        return axios.put(`${apiUrl}/updateEvent`,newEvent)
        .then(response=>{
            let data = response.data;
            console.log(data,"11111111111111111111111111111111111111111111111");
            if (data==="Success"){
                dispatch({type:'UPDATEEVENT_SUCCESS'})
            }
            else{
                dispatch({type:'ADDEVENT_TIMECONFLICT', data})
            }
        })
        .catch(erro =>{
            dispatch({type:'UPDATEEVENT_FAIL'})
        })
    }

}

export const addNewEvent =(event)=>{
    return (dispatch) =>{
        return axios.post(`${apiUrl}/addnewevent`,event)
        .then(response=>{
            let data = response.data;
            if (data==="Success"){
                dispatch({type:'ADDEVENT_SUCCESS',event})
            }
            else{
                dispatch({type:'ADDEVENT_TIMECONFLICT', data})
            }
        })
        .catch(erro =>{
            dispatch({type:'ADDEVENT_FAIL'})
        })
    }

}

export const backhome =()=>{
    return ({type:'BACKHOME'})
}

export const setUpdateToNull =()=>{
    return ({type:'CHECK_AGAIN'})
}

export const handleConflict =()=>{
    return ({type:'CONFLICT'})
}