import React from 'react';
import AdminPanel from '../commonents/admin/AdminPanel';

const AdminPage: React.FC = () => {
  return (
    <div>
      <h4>✏️ 스토어와 상품을 관리할 수 있습니다.</h4>
      <AdminPanel />
    </div>
  );
};

export default AdminPage;
