import React from 'react'


const EventPrev = (props) => {
  const {event} = props
  return (
    <div className="card z-depth-0 event-summary">
      <div className="card-content grey-text text-darken-3">
        <span className="card-title ">{event.eventTitle}</span>
        <h6>StartTime: {event.startTime}</h6>
        <h6>EndTime: {event.endTime}</h6>
        <p>{event.note}</p>
      </div>
    </div>
  )
}

export default EventPrev