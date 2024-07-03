import React from 'react';
import '../../css/gnb.css';

interface GNBProps {
  currentTab: string;
  setTab: (tab: string) => void;
}

const GNB: React.FC<GNBProps> = ({currentTab, setTab}) => {
  return (
    <div className="gnb-container">
      <button className={`gnb-button ${currentTab === 'store' ? 'active' : ''}`} onClick={() => setTab('store')}>
        스토어
      </button>
      <button className={`gnb-button ${currentTab === 'admin' ? 'active' : ''}`} onClick={() => setTab('admin')}>
        어드민
      </button>
    </div>
  );
};

export default GNB;
