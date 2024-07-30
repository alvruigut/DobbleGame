import React from 'react';
import Modal from 'react-modal';

const FinishGameModal = ({ isOpen, onRequestClose, onReturnToMainScreen }) => {
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onRequestClose}
      style={{
        overlay: {
          backgroundColor: 'rgba(255, 255, 255, 0.8)', 
        },
        content: {
          width: '300px',
          margin: 'auto',
          backgroundColor: 'purple',
          color: 'yellow', 
          borderRadius: '10px',
        },
      }}
    >
      <h2>Juego finalizado</h2>
      <p>¡Bien hecho!</p>
      <button
        style={{
          backgroundColor: 'yellow', 
          color: 'purple',
          padding: '10px',
          borderRadius: '5px',
          cursor: 'pointer',
        }}
        onClick={() => {
          onReturnToMainScreen();
          onRequestClose();
        }}
      >
        Ver ganadores
      </button>
    </Modal>
  );
};

export default FinishGameModal;
