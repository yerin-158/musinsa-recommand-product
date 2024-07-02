import React from 'react';

interface GNBProps {
  currentTab: string;
  setTab: (tab: string) => void;
}

const GNB: React.FC<GNBProps> = ({currentTab, setTab}) => {
  return (
    <nav>
      <button onClick={() => setTab('store')} className={currentTab === 'store' ? 'active' : ''}>
        스토어
      </button>
      <button onClick={() => setTab('admin')} className={currentTab === 'admin' ? 'active' : ''}>
        어드민
      </button>
    </nav>
  );
};

export default GNB;
