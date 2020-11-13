import authReducer from './authReducer'
import eventReducer from './eventReducer'
import shareReducer from './shareReducer'
import { combineReducers } from 'redux'

const rootReducer = combineReducers({
    auth: authReducer,
    event: eventReducer,
    share: shareReducer
  });
  
  export default rootReducer
