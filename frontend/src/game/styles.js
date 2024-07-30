export const cardStyle = (violetRadius) => ({
    width: `${violetRadius * 2}px`,
    height: `${violetRadius * 2}px`,
    borderRadius: '50%',
    backgroundColor: 'darkviolet',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: '10px',
  });
  
  export const symbolContainerStyle = (yellowRadius) => ({
    position: 'relative',
    width: `${yellowRadius * 2}px`,
    height: `${yellowRadius * 2}px`,
    borderRadius: '50%',
    backgroundColor: 'yellow',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  });
  
  export const buttonStyle = (x, y, yellowRadius) => ({
    position: 'absolute',
    left: `${x + yellowRadius}px`,
    top: `${y + yellowRadius}px`,
    transform: 'translate(-50%, -50%)',
    border: 'none',
    backgroundColor: 'transparent',
  });
  
  export const imgStyle = {
    width: '50px',
    height: '50px',
    objectFit: 'cover',
    borderRadius: '50%',
    cursor: 'pointer',
  };
  