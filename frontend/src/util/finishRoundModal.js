import React from 'react';
import Modal from 'react-modal';

const FinishRoundModal = ({ isOpen, onRequestClose, onNextRound }) => {
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
      <h2>Ronda finalizada</h2>
      <p>¡Bien hecho! ¿Estás listo para la siguiente ronda?</p>
      <button
        style={{
          backgroundColor: 'yellow', 
          color: 'purple',
          padding: '10px',
          borderRadius: '5px',
          cursor: 'pointer',
        }}
        onClick={() => {
          onNextRound();
          onRequestClose();
        }}
      >
        Siguiente Ronda
      </button>
    </Modal>
  );
};

export default FinishRoundModal;
