const initState = {
    events: [],
    shareError:null,
    shareSuccess:null,
    deleteShareError:null,
    getShareError:null
}

const shareReducer = (state=initState,action) =>{
    switch(action.type){
        case 'GETALL_SHARE':
          return {
            ...state,
            events:action.payload
          }
        case 'GETALLSHARE_FAIL':
          return {
            ...state,
            getShareError:'get share fail'
          }
        case 'DELETE_SHARE':
          return {
            ...state
          }
          case 'DELETESHARE_FAIL':
            return {
              ...state,
              deleteShareError:'delete share fail'
            }
        case 'ADDSHARE_SUCCESS':
          return {
            ...state,
            shareSuccess:'yes'
          }
        case 'ADDSHARE_FAIL':
            return {
              ...state,
              shareError:'add share fail'
            }
        default:
          return state
      }
}

export default shareReducer;