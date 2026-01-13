import React from 'react';
import { Link } from 'react-router-dom';

export default function Dashboard() {
  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-3xl font-bold text-gray-900">RM Dashboard</h2>
        <p className="mt-2 text-sm text-gray-600">
          Relationship Manager Cockpit - AI-Augmented Wealth Management
        </p>
      </div>

      {/* Key Metrics */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
        <MetricCard
          title="Total AUM"
          value="$2.4B"
          change="+12.5%"
          positive={true}
        />
        <MetricCard
          title="Active Clients"
          value="1,247"
          change="+3.2%"
          positive={true}
        />
        <MetricCard
          title="Pending KYC"
          value="23"
          change="-15%"
          positive={true}
        />
        <MetricCard
          title="AI Recommendations"
          value="156"
          change="New"
          positive={true}
        />
      </div>

      {/* Quick Actions */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-3">
        <ActionCard
          title="Client Management"
          description="View and manage HNI/UHNI clients"
          link="/clients"
          icon="👥"
        />
        <ActionCard
          title="Portfolio Analytics"
          description="Real-time portfolio monitoring"
          link="/portfolio"
          icon="📊"
        />
        <ActionCard
          title="AI Advisory"
          description="AI-powered recommendations"
          link="/advisory"
          icon="🤖"
        />
      </div>

      {/* Recent Activities */}
      <div className="bg-white shadow rounded-lg">
        <div className="px-4 py-5 sm:p-6">
          <h3 className="text-lg font-medium text-gray-900 mb-4">
            Recent Activities
          </h3>
          <div className="space-y-3">
            <ActivityItem
              title="Portfolio rebalanced for John Smith"
              time="2 hours ago"
              type="portfolio"
            />
            <ActivityItem
              title="New client onboarded: Sarah Johnson"
              time="5 hours ago"
              type="client"
            />
            <ActivityItem
              title="AI recommendation generated for 3 clients"
              time="1 day ago"
              type="ai"
            />
          </div>
        </div>
      </div>
    </div>
  );
}

function MetricCard({ title, value, change, positive }) {
  return (
    <div className="bg-white overflow-hidden shadow rounded-lg">
      <div className="px-4 py-5 sm:p-6">
        <dt className="text-sm font-medium text-gray-500 truncate">{title}</dt>
        <dd className="mt-1 text-3xl font-semibold text-gray-900">{value}</dd>
        <dd className={`mt-1 text-sm ${positive ? 'text-green-600' : 'text-red-600'}`}>
          {change}
        </dd>
      </div>
    </div>
  );
}

function ActionCard({ title, description, link, icon }) {
  return (
    <Link to={link} className="block">
      <div className="bg-white overflow-hidden shadow rounded-lg hover:shadow-md transition-shadow">
        <div className="px-4 py-5 sm:p-6">
          <div className="text-4xl mb-3">{icon}</div>
          <h3 className="text-lg font-medium text-gray-900">{title}</h3>
          <p className="mt-1 text-sm text-gray-500">{description}</p>
        </div>
      </div>
    </Link>
  );
}

function ActivityItem({ title, time, type }) {
  return (
    <div className="flex items-start space-x-3">
      <div className={`flex-shrink-0 w-2 h-2 mt-2 rounded-full ${
        type === 'portfolio' ? 'bg-blue-500' :
        type === 'client' ? 'bg-green-500' : 'bg-purple-500'
      }`} />
      <div className="flex-1 min-w-0">
        <p className="text-sm text-gray-900">{title}</p>
        <p className="text-xs text-gray-500">{time}</p>
      </div>
    </div>
  );
}
