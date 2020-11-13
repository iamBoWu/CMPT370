const initState = {
    events: [],
    eventError:null,
    addSuccess:null,
    updateSuccess:null,
    conflict:null
}

const eventReducer = (state=initState,action) =>{
    switch(action.type){
        case 'GETALL_EVENT':
          console.log('get all event success');
          return {
            ...state,
            events:action.payload,
            isLogin: 'yes'
          }
        case 'DELETE_EVENT':
          console.log('delete success');
          return {
            ...state,
            isLogin: 'yes'
          }
        case 'ADDEVENT_SUCCESS':
          console.log('add event success');
          return {
            ...state,
            addSuccess:'yes',
            isLogin: 'yes'
          }
        case 'ADDEVENT_TIMECONFLICT':
          console.log('add event has time conflict');
          return {
            ...state,
            addSuccess:'no',
            // updateSuccess:'no',
            isLogin: 'yes',
            conflictEvents:action.data,
            conflict: 'yes'
          }
        case 'ADDEVENT_FAIL':
            console.log('add event fail');
            return {
              ...state,
              eventError:'add event fail',
              isLogin: 'yes'
            }
        case 'UPDATEEVENT_SUCCESS':
              console.log('edit event success');
              return {
                ...state,
                updateSuccess:'yes',
                isLogin: 'yes'
            }
        case 'UPDATEEVENT_FAIL':
              console.log('update event fail');
              return {
                ...state,
                eventError:'update event fail',
                isLogin: 'yes'
            }  
        case 'BACKHOME':
            console.log('backhome');
            return{
                ...state,
                addSuccess:null,
                isLogin: 'yes'
            }
        case 'CHECK_AGAIN':
          return{
              ...state,
              updateSuccess: null
          }
        case 'CONFLICT':
          console.log('handle CONFLICT');
          return{
              ...state,
              conflict: 'no'
          }
        default:
          return state
      }
}

export default eventReducer;