import React, {useEffect, useRef} from 'react';
import '../App.css';
import '../static/css/home/home.css';
import video from '../static/videos/DobbleTrim.mp4'; 

export default function Home(){

    const videoRef = useRef(null);

    useEffect(() => {
        const videoElement = videoRef.current;

        if (videoElement) {
            const handleVideoEnd = () => {
                videoElement.play();
            };
    
            videoElement.addEventListener('ended', handleVideoEnd);

            return () => {
                videoElement.removeEventListener('ended', handleVideoEnd);
            };
        }
    }, []);


    return(
        <div className="home-page-container">
            <div className="fond">
                <h1 style={{ marginTop: '100px', color: 'khaki' }}>DOBBLE</h1>
                <video src={video} controls autoPlay loop ref={videoRef} style={{width: '800px', height: '500px', pointerEvents: 'none' }}/>
                {/* <h3 style={{ marginTop: '-40px' }}>¡Juego divertido e ingenioso!</h3>                 */}
            </div>
        </div>
    );
}