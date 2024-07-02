import React from 'react';
import AdminPanel from '../commonents/admin/AdminPanel';

const AdminPage: React.FC = () => {
  return (
    <div>
      <h4>✏️ 브랜드와 상품을 추가하고 수정하세요!</h4>
      <AdminPanel />
    </div>
  );
};

export default AdminPage;
